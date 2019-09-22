package io.goooler.demoapp.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.database.sqlite.SQLiteDatabase
import android.os.Handler

import com.squareup.leakcanary.LeakCanary

import io.goooler.demoapp.model.Constants

/**
 * 封装通用方法和一些初始化的动作
 */
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initGlobalObject()
        LeakCanary.install(this)
    }

    /**
     * 这里注意此方法仅在模拟器环境下会回调
     */
    override fun onTerminate() {
        super.onTerminate()
        destroyGlobalObject()
    }

    /**
     * 应用启动时初始化全局对象
     */
    private fun initGlobalObject() {
        context = applicationContext
        handler = Handler()
    }

    companion object {
        /**
         * 获取全局 context
         */
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
            private set
        /**
         * 获取全局 handler
         */
        var handler: Handler? = null
            private set

        /**
         * 应用结束时销毁全局对象
         * 真机环境需要在 mainActivity.onDestroy() 执行
         */
        fun destroyGlobalObject() {
            handler?.removeCallbacksAndMessages(null)
        }
    }
}