package io.goooler.demoapp.common

import android.webkit.WebView
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import io.goooler.demoapp.base.core.BaseApplication
import io.goooler.demoapp.common.util.CrashHandler
import io.goooler.demoapp.common.util.ImageLoader

abstract class CommonApplication : BaseApplication() {

  override fun initImmediately() {
    super.initImmediately()
    app = this
    CrashHandler.init()
    initImageLoader()
    initWebView()
    initSmartRefresh()
  }

  private fun initImageLoader() {
    ImageLoader.init(this)
  }

  private fun initWebView() {
    WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG)
  }

  private fun initSmartRefresh() {
    val colors = intArrayOf(android.R.color.transparent, android.R.color.darker_gray)
    SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, refreshLayout ->
      refreshLayout.setPrimaryColorsId(*colors)
      ClassicsHeader(context)
    }
    SmartRefreshLayout.setDefaultRefreshFooterCreator { context, refreshLayout ->
      refreshLayout.setPrimaryColorsId(*colors)
      ClassicsFooter(context)
    }
  }

  companion object {
    lateinit var app: CommonApplication
      private set
  }
}
