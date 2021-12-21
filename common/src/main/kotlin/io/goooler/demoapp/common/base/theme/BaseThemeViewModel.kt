package io.goooler.demoapp.common.base.theme

import androidx.annotation.AnyThread
import io.goooler.demoapp.base.core.BaseViewModel
import io.goooler.demoapp.base.util.MutableBooleanLiveData

abstract class BaseThemeViewModel : BaseViewModel(), ITheme {

  val loading = MutableBooleanLiveData()

  @AnyThread
  override fun showLoading() {
    loading.postValue(true)
  }

  @AnyThread
  override fun hideLoading() {
    loading.postValue(false)
  }
}
