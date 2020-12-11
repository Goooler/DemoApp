package io.goooler.demoapp.base.core

import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Fragment 实现懒加载的基类，在 ViewPager 中使用
 * AndroidX 中懒加载使用新方法，
 * 1. ViewPager 使用 [FragmentStatePagerAdapter] 时传入 BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
 * 2. ViewPager2 使用 [FragmentStateAdapter] 默认就支持
 */
@Suppress("unused")
abstract class BaseLazyFragment : BaseFragment(), ILazyFragment {

  override fun onResume() {
    super.onResume()
    onFragmentResume()
  }
}

interface ILazyFragment {

  /**
   * 用于懒加载
   */
  fun onFragmentResume()
}
