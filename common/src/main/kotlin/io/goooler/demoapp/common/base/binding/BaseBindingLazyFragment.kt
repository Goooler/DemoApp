package io.goooler.demoapp.common.base.binding

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
abstract class BaseBindingLazyFragment<VB : ViewDataBinding> :
  BaseBindingFragment<VB>(),
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
