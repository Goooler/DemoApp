package io.goooler.demoapp.main.vm

import android.app.Application
import androidx.lifecycle.viewModelScope
import io.goooler.demoapp.R
import io.goooler.demoapp.base.BaseViewModel
import io.goooler.demoapp.base.MutableStringLiveData
import io.goooler.demoapp.main.bean.RepoListBean
import io.goooler.demoapp.main.repository.MainRepository
import io.goooler.demoapp.util.isNotNullOrEmpty
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class MainViewModel(application: Application) : BaseViewModel(application) {

    val title = MutableStringLiveData()

    fun initData() {
        requestWithKt()
    }

    /**
     * 协程请求处理
     */
    private fun requestWithKt() {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val google = async(SupervisorJob()) { MainRepository.getRepoListKt("google") }
                val microsoft = async(SupervisorJob()) { MainRepository.getRepoListKt("microsoft") }
                val apple = async(SupervisorJob()) { MainRepository.getRepoListKt("apple") }
                val facebook = async(SupervisorJob()) { MainRepository.getRepoListKt("facebook") }
                val twitter = async(SupervisorJob()) { MainRepository.getRepoListKt("twitter") }

                processList(google, microsoft, apple, facebook, twitter).collect {
                    title.postValue(it)
                }
                MainRepository.storeRepos(google.await())
            } catch (e: Exception) {
                title.postValue(getString(R.string.request_failed))
                showToast(e.message)
            }
        }
    }

    /**
     * flow 处理事件
     */
    private fun processList(vararg lists: Deferred<List<RepoListBean>>) = flow {
        StringBuilder().run {
            lists.forEach {
                append(it.await().firstOrNull()?.name).append("\n")
            }
            emit(toString())
        }
    }

    /**
     * RxJava 请求处理
     */
    private fun requestWithRx() {
        MainRepository.getRepoListRx()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .filter { it.isNotNullOrEmpty() }
            .map { it.firstOrNull()?.name.orEmpty() }
            .subscribe({
                title.value = it
            }, {
                title.value = getString(R.string.request_failed)
                showToast(it.message)
            })
            .let {
                addDisposable(it)
            }
    }
}