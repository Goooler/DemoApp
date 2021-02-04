@file:Suppress("KDocUnresolvedReference")

package io.goooler.demoapp.base.core

/**
 * Fragment 实现懒加载的基类，在 ViewPager 中使用
 * AndroidX 中懒加载使用新方法，
 * 1. ViewPager 使用 [androidx.fragment.app.FragmentStatePagerAdapter] 时传入 BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
 * 2. ViewPager2 使用 [androidx.viewpager2.adapter.FragmentStateAdapter] 默认就支持
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

  fun onFragmentResume()

  fun onFragmentPause() {}
}
