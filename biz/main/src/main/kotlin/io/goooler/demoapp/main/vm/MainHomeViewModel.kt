package io.goooler.demoapp.main.vm

import androidx.lifecycle.viewModelScope
import io.goooler.demoapp.base.core.BaseViewModel
import io.goooler.demoapp.common.util.showToast
import io.goooler.demoapp.main.bean.MainRepoListBean
import io.goooler.demoapp.main.repository.MainCommonRepository
import java.util.concurrent.CancellationException
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainHomeViewModel : BaseViewModel() {
  private val repository = MainCommonRepository()

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
      countdownJob?.cancel(ManualCancellationException)
    }
  }

  private fun startCountDown(
    timeout: Duration = 5.seconds,
  ) {
    countdownJob = viewModelScope.launch {
      flow {
        (timeout.inWholeSeconds downTo Duration.ZERO.inWholeSeconds).forEach {
          delay(1000)
          emit("Timeout \n${it}s")
        }
      }.flowOn(Dispatchers.Default)
        .onCompletion { cause ->
          if (cause == null) {
            _title.value = "Countdown end"
          } else if (cause == ManualCancellationException) {
            _title.value = "Countdown canceled"
          }
        }
        .collect {
          _title.value = it
        }
    }
  }

  private fun fetchRepoLists() {
    viewModelScope.launch {
      try {
        val google = async(SupervisorJob()) { repository.getRepoListFromDb("google") }
        val microsoft = async(SupervisorJob()) { repository.getRepoListFromDb("microsoft") }

        _title.value = processList(google.await(), microsoft.await())
      } catch (e: Exception) {
        _title.value = e.message.orEmpty()
        e.printStackTrace()
      }

      try {
        val google = async(SupervisorJob()) { repository.getRepoListFromApi("google") }
        val microsoft = async(SupervisorJob()) { repository.getRepoListFromApi("microsoft") }

        _title.value = processList(google.await(), microsoft.await())

        putRepoListIntoDb(google.await(), microsoft.await())
      } catch (e: Exception) {
        _title.value = e.message.orEmpty()
        e.printStackTrace()
      }
    }
  }

  private suspend fun processList(vararg lists: List<MainRepoListBean>): String =
    withContext(Dispatchers.Default) {
      lists.fold("") { acc, list ->
        acc + list.lastOrNull()?.name + "\n"
      }
    }

  private suspend fun putRepoListIntoDb(vararg lists: List<MainRepoListBean>) {
    lists.forEach {
      repository.putRepoListIntoDb(it)
    }
  }

  private object ManualCancellationException : CancellationException("cancelManually")
}
