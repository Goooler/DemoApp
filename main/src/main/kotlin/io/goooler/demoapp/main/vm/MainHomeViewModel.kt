package io.goooler.demoapp.main.vm

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.goooler.demoapp.base.util.MutableStringLiveData
import io.goooler.demoapp.base.util.defaultAsync
import io.goooler.demoapp.common.base.theme.BaseRxViewModel
import io.goooler.demoapp.common.util.showToast
import io.goooler.demoapp.main.R
import io.goooler.demoapp.main.bean.MainRepoListBean
import io.goooler.demoapp.main.repository.MainCommonRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@HiltViewModel
class MainHomeViewModel @Inject constructor(private val repository: MainCommonRepository) :
  BaseRxViewModel() {

  val title = MutableStringLiveData()
  var countdownJob: Job? = null

  fun initData() {
    requestWithCr()
  }

  fun startCountDown(
    countDownTime: Second = Second(30),
    callback: (CountDownState) -> Unit = {}
  ) {
    val timeEnd = Second(0)
    countdownJob = viewModelScope.launch {
      flow {
        (countDownTime.value downTo timeEnd.value).forEach {
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
        .collect(title::setValue)
    }
  }

  private fun requestWithCr() {
    viewModelScope.launch(Dispatchers.IO) {
      try {
        val google = defaultAsync { repository.getRepoListFromDb("google") }
        val microsoft = defaultAsync { repository.getRepoListFromDb("microsoft") }

        processList(google.await(), microsoft.await()).collect(title::postValue)
      } catch (_: Exception) {
      }

      try {
        val google = defaultAsync { repository.getRepoListWithCr("google") }
        val microsoft = defaultAsync { repository.getRepoListWithCr("microsoft") }

        processList(google.await(), microsoft.await()).collect(title::postValue)

        putRepoListIntoDb(google.await(), microsoft.await())
      } catch (e: Exception) {
        title.postValue(e.message)
        R.string.common_request_failed.showToast()
      }
    }
  }

  private suspend fun processList(vararg lists: List<MainRepoListBean>) = flow {
    StringBuilder().run {
      lists.forEach {
        append(it.lastOrNull()?.name).append("\n")
      }
      emit(toString())
    }
  }

  private suspend fun putRepoListIntoDb(vararg lists: List<MainRepoListBean>) {
    lists.forEach {
      repository.putRepoListIntoDb(it)
    }
  }

  private fun requestWithRx() {
    Single.zip(
      repository.getRepoListWithRx("google"),
      repository.getRepoListWithRx("microsoft"),
      { google, microsoft ->
        """
        ${google.lastOrNull()?.owner?.avatarUrl}
        ${microsoft.lastOrNull()?.owner?.avatarUrl}
        """.trimIndent()
      }
    ).subscribe(
      title::postValue
    ) {
      title.postValue(it.message)
      R.string.common_request_failed.showToast()
    }.autoDispose()
  }

  enum class CountDownState {
    Start, End, Cancel
  }

  @JvmInline
  value class Second(val value: Int)

  companion object {
    const val CANCEL_MANUALLY = "cancelManually"
  }
}
