package io.goooler.demoapp.main.vm

import android.app.Application
import androidx.lifecycle.viewModelScope
import io.goooler.demoapp.R
import io.goooler.demoapp.base.core.BaseViewModel
import io.goooler.demoapp.base.model.MutableStringLiveData
import io.goooler.demoapp.base.util.defaultAsync
import io.goooler.demoapp.base.util.isNotNullOrEmpty
import io.goooler.demoapp.base.util.subscribeAndObserve
import io.goooler.demoapp.main.api.RepoList
import io.goooler.demoapp.main.repository.MainRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : BaseViewModel(application) {

    val title = MutableStringLiveData()

    fun initData() {
        requestWithCr()
    }

    /**
     * 协程请求处理
     */
    private fun requestWithCr() {
        viewModelScope.launch {
            try {
                val google = defaultAsync { MainRepository.getRepoListCr("google") }
                val microsoft = defaultAsync { MainRepository.getRepoListCr("microsoft") }
                val apple = defaultAsync { MainRepository.getRepoListCr("apple") }
                val facebook = defaultAsync { MainRepository.getRepoListCr("facebook") }
                val twitter = defaultAsync { MainRepository.getRepoListCr("twitter") }

                processList(google, microsoft, apple, facebook, twitter).collect {
                    title.value = it
                }
                //MainRepository.saveReposToDB(google.await())
            } catch (e: Exception) {
                title.value = getString(R.string.request_failed)
                showToast(e.message)
            }
        }
    }

    /**
     * flow 处理事件
     */
    private fun processList(vararg lists: Deferred<RepoList>) = flow {
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
            .subscribeAndObserve()
            .filter { it.isNotNullOrEmpty() }
            .map { it.firstOrNull()?.name.orEmpty() }
            .subscribe({
                title.value = it
            }, {
                title.value = getString(R.string.request_failed)
                showToast(it.message)
            }).add()
    }
}