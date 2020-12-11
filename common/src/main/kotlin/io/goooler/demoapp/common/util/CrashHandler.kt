package io.goooler.demoapp.common.util

import android.os.SystemClock
import android.util.Log
import kotlin.system.exitProcess

/**
 * 捕获系统崩溃
 */
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
    SystemClock.sleep(2000)
    // 如果系统提供了默认的异常处理器，则交给系统去结束程序，否则就由自己结束自己
    if (defaultHandler != null) {
      defaultHandler!!.uncaughtException(t, e)
    } else {
      android.os.Process.killProcess(android.os.Process.myPid())
      exitProcess(1)
    }
  }
}
