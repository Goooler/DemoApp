package io.goooler.demoapp.base.core

import android.annotation.SuppressLint
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
    }

    /**
     * 延迟初始化
     */
    @WorkerThread
    protected open fun initLater() {
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