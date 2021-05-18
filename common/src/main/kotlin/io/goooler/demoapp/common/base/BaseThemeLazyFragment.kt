package io.goooler.demoapp.common.base

import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import io.goooler.demoapp.adapter.vp.CommonFragmentStateAdapter
import io.goooler.demoapp.adapter.vp.CommonFragmentStatePagerAdapter
import io.goooler.demoapp.base.core.ILazyFragment

/**
 * [ViewPager] 搭配 [CommonFragmentStatePagerAdapter] 懒加载
 * [ViewPager2] 搭配 [CommonFragmentStateAdapter] 懒加载
 */
abstract class BaseThemeLazyFragment<VB : ViewDataBinding> :
  BaseThemeFragment<VB>(),
  ILazyFragment {

  @CallSuper
  override fun onResume() {
    super.onResume()
    onFragmentResume()
  }

  @CallSuper
  override fun onPause() {
    super.onPause()
    onFragmentPause()
  }
}
