package io.goooler.demoapp.common.base.theme

import androidx.annotation.MainThread
import io.goooler.demoapp.base.core.BaseFragment

abstract class BaseThemeFragment : BaseFragment(), ITheme {

  @MainThread
  override fun showLoading() {
  }

  @MainThread
  override fun hideLoading() {
  }
}
