package io.goooler.demoapp.adapter.vp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter

/**
 * 继承自 FragmentStatePagerAdapter，会销毁 fragment
 */
@Suppress("DEPRECATION", "unused")
abstract class BaseFragmentPagerAdapter(
    fragmentManager: FragmentManager,
    behavior: Int = BEHAVIOR_SET_USER_VISIBLE_HINT
) : FragmentStatePagerAdapter(fragmentManager, behavior) {

    private val fragmentList = ArrayList<Fragment>()

    open fun setData(list: List<Fragment>) {
        fragmentList.run {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    override fun getItem(position: Int) = fragmentList[position]

    override fun getCount() = fragmentList.size

    override fun getItemPosition(any: Any) = PagerAdapter.POSITION_NONE
}

/**
 *
 */
abstract class BaseLazyFragmentPagerAdapter(fragmentManager: FragmentManager) :
    BaseFragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)