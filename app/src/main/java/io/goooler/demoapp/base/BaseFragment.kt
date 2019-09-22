package io.goooler.demoapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

import io.goooler.demoapp.util.ToastUtil

/**
 * 基类，封装通用方法
 */
abstract class BaseFragment : Fragment() {
    private var viewCreated: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = initView(inflater, container, savedInstanceState)
        viewCreated = true
        return view
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
    protected abstract fun initView(inflater: LayoutInflater,
                                    container: ViewGroup?,
                                    savedInstanceState: Bundle?): View

    /**
     * 用在懒加载加载数据时
     */
    protected abstract fun loadData()

    protected fun showToast(@StringRes textId: Int) {
        ToastUtil.showToast(textId)
    }

    protected fun showToast(text: String) {
        ToastUtil.showToast(text)
    }
}
