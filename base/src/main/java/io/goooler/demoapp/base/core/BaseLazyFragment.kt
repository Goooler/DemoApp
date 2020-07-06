package io.goooler.demoapp.base.core


/**
 * Fragment 实现懒加载的基类，viewPager 中使用
 * AndroidX 中懒加载使用新方法，构造 FragmentPagerAdapter 时传入 BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
 */
abstract class BaseLazyFragment : BaseFragment() {

    /**
     * 用于懒加载
     */
    protected abstract fun loadData()

    override fun onResume() {
        super.onResume()
        loadData()
    }
}