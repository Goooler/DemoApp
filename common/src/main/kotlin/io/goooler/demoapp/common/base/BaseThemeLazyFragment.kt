package io.goooler.demoapp.common.base

import androidx.databinding.ViewDataBinding
import io.goooler.demoapp.base.core.ILazyFragment

abstract class BaseThemeLazyFragment<VB : ViewDataBinding> :
  BaseThemeFragment<VB>(),
  ILazyFragment {

  override fun onResume() {
    super.onResume()
    onFragmentResume()
  }

  override fun onPause() {
    super.onPause()
    onFragmentPause()
  }
}
