package io.goooler.demoapp.main.vm

import android.app.Application
import androidx.lifecycle.viewModelScope
import io.goooler.demoapp.base.util.MutableStringLiveData
import io.goooler.demoapp.base.util.defaultAsync
import io.goooler.demoapp.common.base.BaseRxViewModel
import io.goooler.demoapp.common.network.RetrofitHelper
import io.goooler.demoapp.common.util.showToast
import io.goooler.demoapp.main.R
import io.goooler.demoapp.main.bean.MainRepoListBean
import io.goooler.demoapp.main.db.MainDatabase
import io.goooler.demoapp.main.repository.MainCommonRepository
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class MainHomeViewModel(application: Application) : BaseRxViewModel(application) {

    private val repository =
        MainCommonRepository(RetrofitHelper.create(), MainDatabase.getInstance(application))

    val title = MutableStringLiveData()
    val oneplusUrl = MutableStringLiveData()
    var countdownJob: Job? = null

    fun initData() {
        oneplusUrl.value =
            "https://image01.oneplus.cn/shop/202010/15/1-M00-0C-89-rBqgjV-H7t-AI-nBAAiPBEq6NTM960.jpg"
        requestWithCr()
    }

    fun getRepoListFromDb() {
        viewModelScope.launch {
            repository.getRepoListFromDb().first().name?.showToast()
        }
    }

    fun getRepoListFromDs() {
        viewModelScope.launch {
            repository.getRepoListFromDs().collectLatest {
                it.first().name?.showToast()
            }
        }
    }

    fun startCountDown(countDownTime: Int = 30, callback: (CountDownState) -> Unit) {
        countdownJob = viewModelScope.launch {
            flow {
                (countDownTime downTo 0).forEach {
                    delay(1000)
                    emit("正在测试中\n${it}s")
                }
            }.flowOn(Dispatchers.Default)
                .onStart {
                    callback(CountDownState.Start)
                }
                .onCompletion { cause ->
                    val state = if (cause == null || cause.message == CANCEL_MANUALLY) {
                        title.postValue("手动结束")
                        CountDownState.End
                    } else {
                        CountDownState.Cancel
                    }
                    callback(state)
                }
                .collect {
                    title.value = it
                }
        }
    }

    /**
     * 协程请求处理
     */
    private fun requestWithCr() {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val google = defaultAsync { repository.getRepoListWithCr("google") }
                val microsoft = defaultAsync { repository.getRepoListWithCr("microsoft") }
                val apple = defaultAsync { repository.getRepoListWithCr("apple") }
                val facebook = defaultAsync { repository.getRepoListWithCr("facebook") }
                val twitter = defaultAsync { repository.getRepoListWithCr("twitter") }

                processList(google, microsoft, apple, facebook, twitter).collect {
                    title.postValue(it)
                }

                repository.insertRepoListIntoDb(google.await())
                repository.storeRepoListToDs(google.await())
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
            repository.getRepoListWithRx("google"),
            repository.getRepoListWithRx("microsoft"),
            repository.getRepoListWithRx("apple"),
            repository.getRepoListWithRx("facebook"),
            repository.getRepoListWithRx("twitter"),
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
            })
            .autoDispose()
    }

    enum class CountDownState {
        Start, End, Cancel
    }

    companion object {
        const val CANCEL_MANUALLY = "cancelManually"
    }
}