package io.goooler.demoapp.common.base

import androidx.databinding.ViewDataBinding
import io.goooler.demoapp.base.core.ILazyFragment

abstract class BaseThemeLazyFragment<B : ViewDataBinding> : BaseThemeFragment<B>(), ITheme,
  ILazyFragment {

  override fun onResume() {
    super.onResume()
    onFragmentResume()
  }
}
