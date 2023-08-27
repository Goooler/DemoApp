package io.goooler.demoapp.common.util

import android.os.Process
import android.os.SystemClock
import android.util.Log
import kotlin.system.exitProcess

object CrashHandler : Thread.UncaughtExceptionHandler {

  private var defaultHandler: Thread.UncaughtExceptionHandler? = null

  fun init() {
    defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
    // 将当前应用异常处理器改为默认的
    Thread.setDefaultUncaughtExceptionHandler(this)
  }

  override fun uncaughtException(t: Thread, e: Throwable) {
    Log.getStackTraceString(e)
    // 延时2秒杀死进程
    @Suppress("MagicNumber")
    SystemClock.sleep(2000)
    // 如果系统提供了默认的异常处理器，则交给系统去结束程序，否则就由自己结束自己
    defaultHandler?.uncaughtException(t, e) ?: run {
      Process.killProcess(Process.myPid())
      exitProcess(1)
    }
  }
}
