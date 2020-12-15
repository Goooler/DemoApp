package io.goooler.demoapp.common.base

import io.goooler.demoapp.base.core.BaseViewModel

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseThemeViewModel : BaseViewModel(), ITheme {

  override fun showLoading() {
  }

  override fun hideLoading() {
  }
}
