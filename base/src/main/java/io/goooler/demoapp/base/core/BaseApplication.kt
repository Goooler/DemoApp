package io.goooler.demoapp.base.core

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import android.webkit.WebView
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.multidex.MultiDexApplication
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 封装通用方法和一些初始化的动作
 */
abstract class BaseApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        initRight()
        GlobalScope.launch {
            delay(2000)
            initLater()
        }
    }

    /**
     * 立即初始化
     */
    @MainThread
    protected open fun initRight() {
        context = this
        initWebView()
    }

    /**
     * 延迟初始化
     */
    @WorkerThread
    protected open fun initLater() {
    }

    /**
     * 9.0 以上行为变更，不可多进程使用同一个 webView 目录
     */
    private fun initWebView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val processName = Application.getProcessName()
            if (processName != packageName) {
                WebView.setDataDirectorySuffix(processName)
            }
        }
    }

    companion object {
        /**
         * 获取全局 context
         */
        @SuppressLint("StaticFieldLeak")
        lateinit var context: BaseApplication
            private set
    }
}