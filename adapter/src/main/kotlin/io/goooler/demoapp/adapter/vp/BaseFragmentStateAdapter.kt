package io.goooler.demoapp.adapter.vp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * viewPager2 的 FragmentStateAdapter 默认就可在 Fragment#onResume 中实现懒加载
 */
abstract class BaseFragmentStateAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragmentList = ArrayList<Fragment>()

    open fun setData(fragments: List<Fragment>? = null) {
        fragments?.let {
            fragmentList.clear()
            fragmentList.addAll(it)
        }
        notifyDataSetChanged()
    }

    override fun createFragment(position: Int): Fragment = fragmentList[position]

    override fun getItemCount(): Int = fragmentList.size
}

/**
 * 通用 adapter
 */
class CommonFragmentStateAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    BaseFragmentStateAdapter(fragmentManager, lifecycle)