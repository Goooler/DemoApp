package io.goooler.demoapp.base.core

import android.content.ComponentName
import android.content.Intent
import android.os.Build
import androidx.annotation.IdRes
import androidx.annotation.MainThread
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import io.goooler.demoapp.base.util.showToastInMainThread

/**
 * Fragment 基类，封装通用方法
 */
@Suppress("unused")
abstract class BaseFragment : Fragment() {

    /**
     * 调用 activity 的返回
     */
    protected fun onBackPressed() {
        activity?.onBackPressed()
    }

    /**
     * 调用 activity 的结束
     */
    protected fun finish() {
        activity?.finish()
    }

    protected fun startService(service: Intent): ComponentName? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            activity?.startForegroundService(service)
        } else {
            activity?.startService(service)
        }
    }

    /**
     * @param containerViewId   容器 id
     * @param fragment          要添加的 fragment
     * @param isAddToBackStack  将要添加的 fragment 是否要添加到返回栈
     * @param tag               fragment 的 tag
     */
    protected fun addFragment(
        @IdRes containerViewId: Int,
        fragment: Fragment,
        isAddToBackStack: Boolean = false,
        tag: String? = null
    ) {
        childFragmentManager.commit {
            if (isAddToBackStack) {
                addToBackStack(tag)
            }
            add(containerViewId, fragment, tag)
        }
    }

    /**
     * @param containerViewId   容器 id
     * @param fragment          要替换的 fragment
     * @param isAddToBackStack  将要替换的 fragment 是否要添加到返回栈
     * @param tag               fragment 的 tag
     */
    protected fun replaceFragment(
        @IdRes containerViewId: Int,
        fragment: Fragment,
        isAddToBackStack: Boolean = true,
        tag: String? = null
    ) {
        childFragmentManager.commit {
            if (isAddToBackStack) {
                addToBackStack(tag)
            }
            replace(containerViewId, fragment, tag)
        }
    }

    @MainThread
    protected fun showToast(@StringRes strResId: Int) {
        showToast(getString(strResId))
    }

    @MainThread
    protected fun showToast(text: String) {
        text.showToastInMainThread(requireContext())
    }
}