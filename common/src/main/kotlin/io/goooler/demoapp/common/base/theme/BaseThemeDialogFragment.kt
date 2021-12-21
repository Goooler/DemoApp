package io.goooler.demoapp.common.base.theme

import androidx.annotation.MainThread
import io.goooler.demoapp.base.core.BaseDialogFragment

abstract class BaseThemeDialogFragment : BaseDialogFragment(), ITheme {

  @MainThread
  override fun showLoading() {
  }

  @MainThread
  override fun hideLoading() {
  }
}
