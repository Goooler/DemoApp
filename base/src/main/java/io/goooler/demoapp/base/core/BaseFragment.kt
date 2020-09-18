package io.goooler.demoapp.base.core

import android.content.ComponentName
import android.content.Intent
import android.os.Build
import androidx.annotation.MainThread
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
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

    @MainThread
    protected fun showToast(@StringRes strResId: Int) {
        showToast(getString(strResId))
    }

    @MainThread
    protected fun showToast(text: String) {
        text.showToastInMainThread(requireContext())
    }
}