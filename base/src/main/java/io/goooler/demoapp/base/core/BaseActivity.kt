package io.goooler.demoapp.base.core

import android.content.ComponentName
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.AdaptScreenUtils
import com.blankj.utilcode.util.BarUtils

/**
 * Activity 基类，封装通用方法
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
abstract class BaseActivity : AppCompatActivity() {

    val originalResources: Resources get() = super.getResources()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 首次安装立即打开，后续切后台没有杀死 app 的情况下会触发反复进入 launcherActivity 的 bug
        if ((intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish()
            return
        }
        window.run {
            setBackgroundDrawable(null)
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        }
        BarUtils.transparentStatusBar(this)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun startService(service: Intent): ComponentName? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            super.startForegroundService(service)
        } else {
            super.startService(service)
        }
    }

    override fun getResources(): Resources = AdaptScreenUtils.adaptWidth(originalResources, 360)
}