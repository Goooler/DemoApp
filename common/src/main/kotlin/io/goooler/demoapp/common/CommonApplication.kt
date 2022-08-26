package io.goooler.demoapp.common

import android.os.StrictMode
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import io.goooler.demoapp.base.core.BaseApplication
import io.goooler.demoapp.common.util.CrashHandler
import io.goooler.demoapp.common.util.ImageLoader
import java.util.Locale

abstract class CommonApplication : BaseApplication() {

  override fun onCreate() {
    super.onCreate()
    app = this
    resources.configuration.setLocale(Locale.ENGLISH)
    CrashHandler.init()
    ImageLoader.init(this)
    initSmartRefresh()
    enableStrictMode()
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

  private fun enableStrictMode() {
    if (BuildConfig.DEBUG) {
      val threadPolicy = StrictMode.ThreadPolicy.Builder()
        .detectAll()
        .penaltyLog()
        .build()
      StrictMode.setThreadPolicy(threadPolicy)
    }
  }

  companion object {
    lateinit var app: CommonApplication
      private set
  }
}
