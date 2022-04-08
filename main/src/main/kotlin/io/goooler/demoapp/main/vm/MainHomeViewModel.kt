package io.goooler.demoapp.main.vm

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.goooler.demoapp.base.util.defaultAsync
import io.goooler.demoapp.common.base.theme.BaseThemeViewModel
import io.goooler.demoapp.common.util.showToast
import io.goooler.demoapp.main.bean.MainRepoListBean
import io.goooler.demoapp.main.repository.MainCommonRepository
import java.util.concurrent.CancellationException
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@HiltViewModel
class MainHomeViewModel @Inject constructor(private val repository: MainCommonRepository) :
  BaseThemeViewModel() {

  private val _title = MutableStateFlow("")
  val title: StateFlow<String> = _title

  private var countdownJob: Job? = null

  fun initData() {
    fetchRepoLists()
  }

  fun countDown() {
    if (countdownJob?.isActive != true) {
      startCountDown()
    } else {
      countdownJob?.cancel(CancellationException(CANCEL_MANUALLY))
    }
  }

  private fun startCountDown(
    timeout: Duration = 30.seconds,
    callback: (CountDownState) -> Unit = {}
  ) {
    countdownJob = viewModelScope.launch {
      flow {
        (timeout.inWholeSeconds downTo Duration.ZERO.inWholeSeconds).forEach {
          delay(1000)
          emit("正在测试中\n${it}s")
        }
      }.flowOn(Dispatchers.Default)
        .onStart {
          callback(CountDownState.Start)
        }
        .onCompletion { cause ->
          val state = if (cause == null || cause.message == CANCEL_MANUALLY) {
            _title.emit("倒计时结束")
            CountDownState.End
          } else {
            CountDownState.Cancel
          }
          callback(state)
        }
        .collect(_title::emit)
    }
  }

  private fun fetchRepoLists() {
    viewModelScope.launch(Dispatchers.IO) {
      try {
        val google = defaultAsync { repository.getRepoListFromDb("google") }
        val microsoft = defaultAsync { repository.getRepoListFromDb("microsoft") }

        processList(google.await(), microsoft.await()).collect(_title::emit)
      } catch (_: Exception) {
      }

      try {
        val google = defaultAsync { repository.getRepoListFromApi("google") }
        val microsoft = defaultAsync { repository.getRepoListFromApi("microsoft") }

        processList(google.await(), microsoft.await()).collect(_title::emit)

        putRepoListIntoDb(google.await(), microsoft.await())
      } catch (e: Exception) {
        e.message?.let {
          _title.emit(it)
        }
        io.goooler.demoapp.common.R.string.common_request_failed.showToast()
      }
    }
  }

  private suspend fun processList(vararg lists: List<MainRepoListBean>): Flow<String> = flow {
    lists.fold("") { acc, list ->
      acc + list.lastOrNull()?.name + "\n"
    }.let {
      emit(it)
    }
  }

  private suspend fun putRepoListIntoDb(vararg lists: List<MainRepoListBean>) {
    lists.forEach {
      repository.putRepoListIntoDb(it)
    }
  }

  enum class CountDownState {
    Start, End, Cancel
  }

  companion object {
    const val CANCEL_MANUALLY = "cancelManually"
  }
}
