package io.goooler.demoapp.common.base.theme

import androidx.annotation.AnyThread
import io.goooler.demoapp.base.core.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseThemeViewModel : BaseViewModel(), ITheme {

  private val _loading = MutableStateFlow(false)
  val loading: StateFlow<Boolean> get() = _loading

  @AnyThread
  override fun showLoading() {
    _loading.value = true
  }

  @AnyThread
  override fun hideLoading() {
    _loading.value = false
  }
}
