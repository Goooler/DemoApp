package io.goooler.demoapp.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.squareup.leakcanary.LeakCanary
import io.goooler.demoapp.util.CrashHandler
import io.goooler.demoapp.util.LogUtil
import io.goooler.demoapp.util.debugRun
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
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
     * 应用启动时初始化
     */
    @SuppressLint("CheckResult")
    private fun initData() {
        context = applicationContext
        CrashHandler.init()
        ARouter.init(this)
        // 部分三方初始化延时处理
        Single.just(true)
                .subscribeOn(Schedulers.io())
                .delay(2, TimeUnit.SECONDS)
                .subscribe({
                    LeakCanary.install(this)
                    debugRun {
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
    }
}