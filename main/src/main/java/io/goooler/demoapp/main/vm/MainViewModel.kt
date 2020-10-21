package io.goooler.demoapp.main.vm

import android.app.Application
import androidx.lifecycle.viewModelScope
import io.goooler.demoapp.base.util.MutableStringLiveData
import io.goooler.demoapp.base.util.defaultAsync
import io.goooler.demoapp.common.base.BaseRxViewModel
import io.goooler.demoapp.common.util.showToast
import io.goooler.demoapp.main.R
import io.goooler.demoapp.main.api.RepoList
import io.goooler.demoapp.main.repository.MainRepository
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlin.math.abs

class MainViewModel(application: Application) : BaseRxViewModel(application) {

    val title = MutableStringLiveData()
    val oneplusUrl = MutableStringLiveData()

    fun initData() {
        oneplusUrl.value =
            "https://image01.oneplus.cn/shop/202010/15/1-M00-0C-89-rBqgjV-H7t-AI-nBAAiPBEq6NTM960.jpg"
        requestWithRx()
    }

    /**
     * 协程请求处理
     */
    private fun requestWithCr() {
        viewModelScope.launch(Dispatchers.Default) {
            abs(1)
            try {
                val google = defaultAsync { MainRepository.getRepoListCr("google") }
                val microsoft = defaultAsync { MainRepository.getRepoListCr("microsoft") }
                val apple = defaultAsync { MainRepository.getRepoListCr("apple") }
                val facebook = defaultAsync { MainRepository.getRepoListCr("facebook") }
                val twitter = defaultAsync { MainRepository.getRepoListCr("twitter") }

                processList(google, microsoft, apple, facebook, twitter).collect {
                    title.postValue(it)
                }
            } catch (e: Exception) {
                title.postValue(e.message)
                showToast(R.string.request_failed)
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
                    ${t1.firstOrNull()?.owner?.avatarUrl}
                    ${t2.firstOrNull()?.owner?.avatarUrl}
                    ${t3.firstOrNull()?.owner?.avatarUrl}
                    ${t4.firstOrNull()?.owner?.avatarUrl}
                    ${t5.firstOrNull()?.owner?.avatarUrl}
                """.trimIndent()
            })
            .subscribe({
                title.postValue(it)
            }, {
                title.postValue(it.message)
                showToast(R.string.request_failed)
            }).add()
    }
}