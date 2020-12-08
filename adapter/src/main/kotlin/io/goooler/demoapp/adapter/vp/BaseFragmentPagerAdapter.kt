@file:Suppress("DEPRECATION", "unused")

package io.goooler.demoapp.adapter.vp

import androidx.annotation.IntRange
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter

/**
 * 继承自 [FragmentStatePagerAdapter]，会销毁 fragment
 */
abstract class BaseFragmentPagerAdapter(
    fragmentManager: FragmentManager,
    behavior: Int = BEHAVIOR_SET_USER_VISIBLE_HINT
) : FragmentStatePagerAdapter(fragmentManager, behavior) {

    private val fragmentList = ArrayList<Fragment>()
    private val titleList = ArrayList<String>()

    open fun setData(fragments: List<Fragment>? = null, titles: List<String>? = null) {
        fragments?.let {
            fragmentList.clear()
            fragmentList.addAll(it)
        }
        titles?.let {
            titleList.clear()
            titleList.addAll(it)
        }
        notifyDataSetChanged()
    }

    override fun getItem(@IntRange(from = 0) position: Int): Fragment = fragmentList[position]

    override fun getPageTitle(@IntRange(from = 0) position: Int): CharSequence? =
        titleList[position]

    override fun getCount(): Int = fragmentList.size

    override fun getItemPosition(any: Any): Int = PagerAdapter.POSITION_NONE
}

/**
 * 懒加载基类
 */
abstract class BaseLazyFragmentPagerAdapter(fragmentManager: FragmentManager) :
    BaseFragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)

/**
 * 通用 adapter
 */
class CommonFragmentPagerAdapter(fragmentManager: FragmentManager) :
    BaseFragmentPagerAdapter(fragmentManager)

/**
 * 通用 lazy adapter
 */
class CommonLazyFragmentPagerAdapter(fragmentManager: FragmentManager) :
    BaseLazyFragmentPagerAdapter(fragmentManager)
