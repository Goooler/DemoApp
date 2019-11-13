package io.goooler.demoapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * 懒加载的 Fragment 基类
 */
abstract class BaseLazyFragment : BaseFragment() {
    private var viewCreated: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = initView()
        viewCreated = true
        return rootView
    }

    /**
     * 当页面可见时调用，用于懒加载
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (viewCreated && isVisibleToUser) {
            loadData()
        }
    }

    /**
     * 默认第一页 fragment 执行，不需要懒加载
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (viewCreated && userVisibleHint) {
            loadData()
        }
    }

    /**
     * onCreateView 中初始化页面的任务放在这里完成，三个参数相同
     *
     * @return 根布局
     */
    protected abstract fun initView(): View

    /**
     * 用在懒加载加载数据时
     */
    protected abstract fun loadData()
}