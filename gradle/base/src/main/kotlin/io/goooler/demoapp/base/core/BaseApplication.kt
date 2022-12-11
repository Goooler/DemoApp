package io.goooler.demoapp.base.core

import android.annotation.TargetApi
import android.app.Application
import android.os.Build
import android.webkit.WebView
import io.goooler.demoapp.base.util.ToastUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

abstract class BaseApplication : Application(), CoroutineScope by MainScope() {

  override fun onCreate() {
    super.onCreate()
    initWebView()

    ToastUtil.show(this, "BaseApplication.onCreate")
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
