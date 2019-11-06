package io.goooler.demoapp.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Handler
import com.alibaba.android.arouter.launcher.ARouter
import com.squareup.leakcanary.LeakCanary
import io.goooler.demoapp.BuildConfig
import io.goooler.demoapp.util.CrashHandler
import io.goooler.demoapp.util.LogUtil
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

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
    @SuppressLint("CheckResult")
    private fun initData() {
        context = applicationContext
        handler = Handler()
        ARouter.init(this)
        Single.just(true)
                .subscribeOn(Schedulers.io())
                .delay(2, TimeUnit.SECONDS)
                .subscribe({
                    CrashHandler.instance.init()
                    LeakCanary.install(this)
                    if (BuildConfig.DEBUG) {
                        Timber.plant(Timber.DebugTree())
                        ARouter.openLog()
                        ARouter.openDebug()
                    }
                }, {
                    LogUtil.d(it)
                })
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