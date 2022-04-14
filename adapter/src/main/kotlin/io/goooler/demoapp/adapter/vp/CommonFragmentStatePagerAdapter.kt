@file:Suppress("unused", "DEPRECATION")

package io.goooler.demoapp.adapter.vp

import androidx.annotation.IntRange
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

/**
 * Adapter for [ViewPager]
 * Lazy load in [Fragment.onResume]
 */
open class CommonFragmentStatePagerAdapter(
  fragmentManager: FragmentManager,
  behavior: Int = BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) : FragmentStatePagerAdapter(fragmentManager, behavior) {

  private val fragmentList = mutableListOf<Fragment>()
  private val titleList = mutableListOf<String>()

  fun setData(fragments: List<Fragment>? = null, titles: List<String>? = null) {
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
