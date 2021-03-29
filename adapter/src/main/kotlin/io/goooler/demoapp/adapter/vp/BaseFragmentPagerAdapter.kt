@file:Suppress("DEPRECATION", "unused")

package io.goooler.demoapp.adapter.vp

import androidx.annotation.IntRange
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter

/**
 * 继承自 [FragmentStatePagerAdapter]，会销毁 fragment
 */
abstract class BaseFragmentPagerAdapter(
  fragmentManager: FragmentManager,
  behavior: Int = BEHAVIOR_SET_USER_VISIBLE_HINT
) : FragmentStatePagerAdapter(fragmentManager, behavior), IFragmentAdapter {

  private val fragmentList = mutableListOf<Fragment>()
  private val titleList = mutableListOf<String>()

  override fun setData(fragments: List<Fragment>?, titles: List<String>?) {
    fragments?.let {
      fragmentList.clear()
      fragmentList += it
    }
    titles?.let {
      titleList.clear()
      titleList += it
    }
    notifyDataSetChanged()
  }

  override fun getItem(@IntRange(from = 0) position: Int): Fragment = fragmentList[position]

  override fun getPageTitle(@IntRange(from = 0) position: Int): CharSequence? =
    titleList[position]

  override fun getCount(): Int = fragmentList.size

  override fun getItemPosition(any: Any): Int = PagerAdapter.POSITION_NONE
}

fun FragmentActivity.getFragmentStatePagerAdapter(
  behavior: Int = FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
): Lazy<BaseFragmentPagerAdapter> = getFragmentStatePagerAdapter(supportFragmentManager, behavior)

fun Fragment.getFragmentStatePagerAdapter(
  behavior: Int = FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
): Lazy<BaseFragmentPagerAdapter> = getFragmentStatePagerAdapter(childFragmentManager, behavior)

fun getFragmentStatePagerAdapter(
  fragmentManager: FragmentManager,
  behavior: Int = FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
): Lazy<BaseFragmentPagerAdapter> = lazy(LazyThreadSafetyMode.NONE) {
  object : BaseFragmentPagerAdapter(fragmentManager, behavior) {}
}

