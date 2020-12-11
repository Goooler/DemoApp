package io.goooler.demoapp.base.core

import android.app.Application
import android.os.Build
import android.webkit.WebView
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class BaseApplication : Application() {

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
    app = this
    initWebView()
  }

  /**
   * 延迟初始化
   */
  @WorkerThread
  protected open suspend fun initLater() {
  }

  /**
   * 9.0 以上行为变更，不可多进程使用同一个 webView 目录
   */
  private fun initWebView() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
      val processName = getProcessName()
      if (processName != packageName) {
        WebView.setDataDirectorySuffix(processName)
      }
    }
  }

  companion object {
    lateinit var app: BaseApplication
      private set
  }
}
