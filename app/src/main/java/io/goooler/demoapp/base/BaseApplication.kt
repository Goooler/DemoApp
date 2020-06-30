package io.goooler.demoapp.base

import android.annotation.SuppressLint
import android.content.Context
import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import io.goooler.demoapp.util.CrashHandler
import io.goooler.demoapp.util.debugRun
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 封装通用方法和一些初始化的动作
 */
class BaseApplication : MultiDexApplication() {

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
        initLater()
    }

    private fun initLater() {
        GlobalScope.launch(Dispatchers.IO) {
            delay(2000)
            debugRun {
                ARouter.openLog()
                ARouter.openDebug()
            }
        }
    }

    companion object {
        /**
         * 获取全局 context
         */
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
}