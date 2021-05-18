package io.goooler.demoapp.common.base

import androidx.annotation.AnyThread
import androidx.annotation.CallSuper
import io.goooler.demoapp.base.core.BaseViewModel
import io.goooler.demoapp.base.util.MutableBooleanLiveData

abstract class BaseThemeViewModel : BaseViewModel(), ITheme {

  val loading = MutableBooleanLiveData()

  @AnyThread
  @CallSuper
  override fun showLoading() {
    loading.postValue(true)
  }

  @AnyThread
  @CallSuper
  override fun hideLoading() {
    loading.postValue(false)
  }
}
