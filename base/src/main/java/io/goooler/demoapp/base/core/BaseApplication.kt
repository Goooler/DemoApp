package io.goooler.demoapp.base.core

import android.annotation.SuppressLint
import androidx.multidex.MultiDexApplication
import com.tencent.mmkv.MMKV
import io.goooler.demoapp.base.util.CrashHandler
import io.goooler.demoapp.base.util.ObjectBox
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 封装通用方法和一些初始化的动作
 */
abstract class BaseApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        initData()
    }

    /**
     * 应用启动时初始化
     */
    @SuppressLint("CheckResult")
    private fun initData() {
        context = this
        CrashHandler.init()
        ObjectBox.init(this)
        MMKV.initialize(this)
        GlobalScope.launch(Dispatchers.IO) {
            delay(2000)
            initLater()
        }
    }

    protected open fun initLater() {}

    companion object {
        /**
         * 获取全局 context
         */
        @SuppressLint("StaticFieldLeak")
        lateinit var context: BaseApplication
            private set
    }
}