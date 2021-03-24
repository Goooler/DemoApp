package io.goooler.demoapp.common.base

import androidx.annotation.ContentView
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import io.goooler.demoapp.base.core.ILazyFragment

abstract class BaseThemeLazyFragment<B : ViewDataBinding>
@ContentView constructor(@LayoutRes layoutId: Int) :
  BaseThemeFragment<B>(layoutId),
  ITheme,
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
