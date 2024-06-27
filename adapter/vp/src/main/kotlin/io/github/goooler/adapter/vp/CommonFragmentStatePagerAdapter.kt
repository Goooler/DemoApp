@file:Suppress("unused", "DEPRECATION")

package io.github.goooler.adapter.vp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

/**
 * Adapter for [ViewPager]
 * Lazy load in [Fragment.onResume]
 */
public open class CommonFragmentStatePagerAdapter(
  fragmentManager: FragmentManager,
  behavior: Int = BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
) : FragmentStatePagerAdapter(fragmentManager, behavior) {

  private val fragmentList = mutableListOf<Fragment>()
  private val titleList = mutableListOf<String>()

  public fun setData(fragments: List<Fragment>? = null, titles: List<String>? = null) {
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

  public override fun getItem(position: Int): Fragment = fragmentList[position]

  public override fun getPageTitle(position: Int): CharSequence? =
    titleList[position]

  public override fun getCount(): Int = fragmentList.size

  public override fun getItemPosition(any: Any): Int = PagerAdapter.POSITION_NONE
}
