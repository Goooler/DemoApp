package io.goooler.demoapp.common.base.theme

import androidx.annotation.AnyThread
import androidx.lifecycle.MutableLiveData
import io.goooler.demoapp.base.core.BaseViewModel

abstract class BaseThemeViewModel : BaseViewModel(), ITheme {

  val loading = MutableLiveData<Boolean>()

  @AnyThread
  override fun showLoading() {
    loading.postValue(true)
  }

  @AnyThread
  override fun hideLoading() {
    loading.postValue(false)
  }
}
