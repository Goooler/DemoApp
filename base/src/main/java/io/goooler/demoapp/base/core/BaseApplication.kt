package io.goooler.demoapp.base.core

import android.annotation.SuppressLint
import android.os.Build
import android.webkit.WebView
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.multidex.MultiDexApplication
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
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
        app = this
        initWebView()
        initSmartRefresh()
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
            val processName = getProcessName()
            if (processName != packageName) {
                WebView.setDataDirectorySuffix(processName)
            }
        }
    }

    private fun initSmartRefresh() {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, refreshLayout ->
            refreshLayout.setPrimaryColorsId(
                android.R.color.transparent, android.R.color.darker_gray
            )
            ClassicsHeader(context)
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, refreshLayout ->
            refreshLayout.setPrimaryColorsId(
                android.R.color.transparent, android.R.color.darker_gray
            )
            ClassicsFooter(context)
        }
    }

    companion object {
        /**
         * 获取全局 context
         */
        @SuppressLint("StaticFieldLeak")
        lateinit var app: BaseApplication
            private set
    }
}