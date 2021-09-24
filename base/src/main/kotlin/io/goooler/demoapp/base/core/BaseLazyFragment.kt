@file:Suppress("KDocUnresolvedReference", "DEPRECATION")

package io.goooler.demoapp.base.core

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.viewpager.widget.ViewPager

/**
 * Fragment 实现懒加载的基类，配合 [ViewPager] 或 [ViewPager2] 使用
 * 这里实现的是最新的懒加载方案，只需要在 [Fragment.onResume] 中分发事件即可
 *
 * 1. [ViewPager] 使用 [FragmentStatePagerAdapter] 时传入 [BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT]
 * 2. [ViewPager2] 使用 [FragmentStateAdapter] 默认就支持
 */
@Suppress("unused")
abstract class BaseLazyFragment : BaseFragment(), ILazyFragment {

  override fun onResume() {
    super.onResume()
    onFragmentResume()
  }

  override fun onPause() {
    super.onPause()
    onFragmentPause()
  }
}

/**
 * 用于懒加载
 */
interface ILazyFragment {

  @MainThread
  fun onFragmentResume()

  @MainThread
  fun onFragmentPause() {}
}
