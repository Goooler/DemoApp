package io.goooler.demoapp.base.core

import android.annotation.TargetApi
import android.app.Application
import android.os.Build
import android.webkit.WebView
import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class BaseApplication : Application(), CoroutineScope by MainScope() {

  override fun onCreate() {
    super.onCreate()
    initImmediately()
    launch(Dispatchers.IO) {
      delay(3.seconds.inWholeMilliseconds)
      initLater()
    }
  }

  /**
   * 立即初始化
   */
  @MainThread
  @CallSuper
  protected open fun initImmediately() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
      initWebView()
    }
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
  @TargetApi(Build.VERSION_CODES.P)
  private fun initWebView() {
    val processName = getProcessName()
    if (processName != packageName) {
      WebView.setDataDirectorySuffix(processName)
    }
  }
}
