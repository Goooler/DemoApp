package io.goooler.demoapp.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Handler
import com.squareup.leakcanary.LeakCanary
import io.goooler.demoapp.BuildConfig
import io.goooler.demoapp.util.CrashHandler
import timber.log.Timber

/**
 * 封装通用方法和一些初始化的动作
 */
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initData()
    }

    /**
     * 这里注意此方法仅在模拟器环境下会回调
     */
    override fun onTerminate() {
        super.onTerminate()
        destroyGlobalObject()
    }

    /**
     * 应用启动时初始化
     */
    private fun initData() {
        context = applicationContext
        handler = Handler()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        LeakCanary.install(this)
        CrashHandler.instance.init()
    }

    companion object {
        /**
         * 获取全局 context
         */
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        /**
         * 获取全局 handler
         */
        lateinit var handler: Handler

        /**
         * 应用结束时销毁全局对象
         */
        fun destroyGlobalObject() {
            handler.removeCallbacksAndMessages(null)
        }
    }
}