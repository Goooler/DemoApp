package io.goooler.demoapp.util

import android.os.SystemClock
import kotlin.system.exitProcess


/**
 * 捕获系统崩溃
 */
object CrashHandler : Thread.UncaughtExceptionHandler {

    private var mDefaultCrashHandler: Thread.UncaughtExceptionHandler? = null

    fun init() {
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler()
        //将当前应用异常处理器改为默认的
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(thread: Thread, ex: Throwable) {
        //延时1秒杀死进程
        SystemClock.sleep(1000)
        //如果系统提供了默认的异常处理器，则交给系统去结束程序，否则就由自己结束自己
        if (mDefaultCrashHandler != null) {
            mDefaultCrashHandler?.uncaughtException(thread, ex)
        } else {
            android.os.Process.killProcess(android.os.Process.myPid())
            exitProcess(1)
        }
    }
}
