package io.goooler.demoapp.main.vm

import android.app.Application
import androidx.lifecycle.viewModelScope
import io.goooler.demoapp.base.core.BaseViewModel
import io.goooler.demoapp.base.util.MutableStringLiveData
import io.goooler.demoapp.base.util.defaultAsync
import io.goooler.demoapp.main.R
import io.goooler.demoapp.main.api.RepoList
import io.goooler.demoapp.main.repository.MainRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : BaseViewModel(application) {

    val title = MutableStringLiveData()

    fun initData() {
        requestWithRx()
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
        Observable.zip(
            MainRepository.getRepoListRx("google"),
            MainRepository.getRepoListRx("microsoft"),
            MainRepository.getRepoListRx("apple"),
            MainRepository.getRepoListRx("facebook"),
            MainRepository.getRepoListRx("twitter"),
            { t1, t2, t3, t4, t5 ->
                """
                    ${t1.firstOrNull()?.name}
                    ${t2.firstOrNull()?.name}
                    ${t3.firstOrNull()?.name}
                    ${t4.firstOrNull()?.name}
                    ${t5.firstOrNull()?.name}
                """.trimIndent()
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                title.value = it
            }, {
                title.value = getString(R.string.request_failed)
                silentThrowable(it)
            }).add()
    }
}