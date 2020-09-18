package io.goooler.demoapp.base.core

import android.content.ComponentName
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.MainThread
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.AdaptScreenUtils
import com.blankj.utilcode.util.BarUtils
import io.goooler.demoapp.base.util.showToastInMainThread

/**
 * Activity 基类，封装通用方法
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
abstract class BaseActivity : AppCompatActivity() {

    protected val originalResources: Resources get() = super.getResources()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.run {
            setBackgroundDrawable(null)
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        }
        BarUtils.transparentStatusBar(this)
    }

    override fun startService(service: Intent): ComponentName? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            super.startForegroundService(service)
        } else {
            super.startService(service)
        }
    }

    override fun getResources(): Resources {
        return AdaptScreenUtils.adaptWidth(originalResources, 360)
    }

    @MainThread
    protected fun showToast(@StringRes strResId: Int) {
        showToast(getString(strResId))
    }

    @MainThread
    protected fun showToast(text: String) {
        text.showToastInMainThread(this)
    }
}