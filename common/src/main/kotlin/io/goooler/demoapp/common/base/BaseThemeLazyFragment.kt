package io.goooler.demoapp.common.base

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import io.goooler.demoapp.base.core.ILazyFragment

abstract class BaseThemeLazyFragment<B : ViewDataBinding>(@LayoutRes layoutId: Int) :
  BaseThemeFragment<B>(layoutId),
  ITheme,
  ILazyFragment {

  override fun onResume() {
    super.onResume()
    onFragmentResume()
  }
}
