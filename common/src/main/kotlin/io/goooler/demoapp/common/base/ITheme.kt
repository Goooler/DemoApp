package io.goooler.demoapp.common.base

import androidx.annotation.MainThread

sealed interface ITheme {

  @MainThread
  fun showLoading()

  @MainThread
  fun hideLoading()
}
