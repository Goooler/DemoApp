package io.goooler.demoapp.base.core

import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.view.KeyEvent
import androidx.fragment.app.Fragment

/**
 * Fragment 基类，封装通用方法
 */
abstract class BaseFragment : Fragment() {

    protected open fun onBackPressed(): Boolean {
        activity?.onBackPressed()
        return true
    }

    protected fun finish() {
        activity?.finish()
    }

    override fun onResume() {
        super.onResume()
        view?.run {
            isFocusableInTouchMode = true
            requestFocus()
            setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) onBackPressed() else false
            }
        }
    }

    protected fun startService(service: Intent): ComponentName? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            activity?.startForegroundService(service)
        } else {
            activity?.startService(service)
        }
    }
}