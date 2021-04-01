package io.goooler.demoapp.main.vm

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.goooler.demoapp.base.util.MutableStringLiveData
import io.goooler.demoapp.base.util.defaultAsync
import io.goooler.demoapp.common.base.BaseRxViewModel
import io.goooler.demoapp.common.util.showToast
import io.goooler.demoapp.main.R
import io.goooler.demoapp.main.bean.MainRepoListBean
import io.goooler.demoapp.main.repository.MainCommonRepository
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainHomeViewModel @Inject constructor(private val repository: MainCommonRepository) :
  BaseRxViewModel() {

  val title = MutableStringLiveData()
  var countdownJob: Job? = null

  fun initData() {
    requestWithCr()
  }

  fun startCountDown(countDownTime: Int = 30, callback: (CountDownState) -> Unit = {}) {
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
    viewModelScope.launch(Dispatchers.IO) {
      try {
        val google = defaultAsync { repository.getRepoListFromDb("google") }
        val microsoft = defaultAsync { repository.getRepoListFromDb("microsoft") }

        processList(google, microsoft).collect {
          title.postValue(it)
        }
      } catch (_: Exception) {
      }

      try {
        val google = defaultAsync { repository.getRepoListWithCr("google") }
        val microsoft = defaultAsync { repository.getRepoListWithCr("microsoft") }

        processList(google, microsoft).collect {
          title.postValue(it)
        }

        putRepoListIntoDb(google, microsoft)
      } catch (e: Exception) {
        title.postValue(e.message)
        R.string.common_request_failed.showToast()
      }
    }
  }

  /**
   * flow 处理事件
   */
  private fun processList(vararg lists: Deferred<List<MainRepoListBean>>) = flow {
    StringBuilder().run {
      lists.forEach {
        append(it.await().lastOrNull()?.name).append("\n")
      }
      emit(toString())
    }
  }

  private suspend fun putRepoListIntoDb(vararg lists: Deferred<List<MainRepoListBean>>) {
    lists.forEach {
      repository.putRepoListIntoDb(it.await())
    }
  }

  /**
   * RxJava 请求处理
   */
  private fun requestWithRx() {
    Single.zip(
      repository.getRepoListWithRx("google"),
      repository.getRepoListWithRx("microsoft"),
      { t1, t2 ->
        """
        ${t1.lastOrNull()?.owner?.avatarUrl}
        ${t2.lastOrNull()?.owner?.avatarUrl}
        """.trimIndent()
      }
    )
      .subscribe(
        {
          title.postValue(it)
        },
        {
          title.postValue(it.message)
          R.string.common_request_failed.showToast()
        }
      )
      .autoDispose()
  }

  enum class CountDownState {
    Start, End, Cancel
  }

  companion object {
    const val CANCEL_MANUALLY = "cancelManually"
  }
}
