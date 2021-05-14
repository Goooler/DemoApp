package io.goooler.demoapp.adapter.vp

import androidx.annotation.IntRange
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

/**
 * [ViewPager2] 使用
 * 可在 [Fragment.onResume] 中实现懒加载
 */
open class CommonFragmentStateAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
  FragmentStateAdapter(fragmentManager, lifecycle), IFragmentAdapter {

  private val fragmentList = mutableListOf<Fragment>()

  override fun setData(fragments: List<Fragment>?, titles: List<String>?) {
    fragments?.let {
      fragmentList.clear()
      fragmentList += it
    }
    notifyDataSetChanged()
  }

  override fun getItem(@IntRange(from = 0) position: Int): Fragment = fragmentList[position]

  override fun createFragment(@IntRange(from = 0) position: Int): Fragment =
    fragmentList[position]

  override fun getItemCount(): Int = fragmentList.size
}
