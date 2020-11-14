package io.goooler.demoapp.main.vm

import android.app.Application
import androidx.lifecycle.viewModelScope
import io.goooler.demoapp.base.util.MutableStringLiveData
import io.goooler.demoapp.base.util.defaultAsync
import io.goooler.demoapp.common.base.BaseRxViewModel
import io.goooler.demoapp.common.util.showToast
import io.goooler.demoapp.main.R
import io.goooler.demoapp.main.bean.MainRepoListBean
import io.goooler.demoapp.main.repository.MainCommonRepository
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainHomeViewModel(application: Application) : BaseRxViewModel(application) {

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
            try {
                val google = defaultAsync { MainCommonRepository.getRepoListCr("google") }
                val microsoft = defaultAsync { MainCommonRepository.getRepoListCr("microsoft") }
                val apple = defaultAsync { MainCommonRepository.getRepoListCr("apple") }
                val facebook = defaultAsync { MainCommonRepository.getRepoListCr("facebook") }
                val twitter = defaultAsync { MainCommonRepository.getRepoListCr("twitter") }

                processList(google, microsoft, apple, facebook, twitter).collect {
                    title.postValue(it)
                }
            } catch (e: Exception) {
                title.postValue(e.message)
                R.string.request_failed.showToast()
            }
        }
    }

    /**
     * flow 处理事件
     */
    private fun processList(vararg lists: Deferred<List<MainRepoListBean>>) = flow {
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
            MainCommonRepository.getRepoListRx("google"),
            MainCommonRepository.getRepoListRx("microsoft"),
            MainCommonRepository.getRepoListRx("apple"),
            MainCommonRepository.getRepoListRx("facebook"),
            MainCommonRepository.getRepoListRx("twitter"),
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
                R.string.request_failed.showToast()
            }).add()
    }
}