package io.goooler.demoapp.web

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.net.Uri
import android.net.http.SslError
import android.util.AttributeSet
import android.view.ViewGroup
import android.webkit.SslErrorHandler
import android.webkit.URLUtil
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.webkit.WebViewClientCompat

@Suppress("SetJavaScriptEnabled")
open class CompatWebView(context: Context, attrs: AttributeSet? = null) : WebView(context, attrs) {

  var onEventListener: OnEventListener? = null

  init {
    initWebViewSettings()
  }

  open fun onDestroy() {
    stopLoading()
    (parent as? ViewGroup)?.removeView(this)
    removeAllViews()
    destroy()
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    attachToLifecycle()
  }

  private fun initWebViewSettings() {
    settings.run {
      // 允许 js 运行
      javaScriptEnabled = true
      // 支持通过 js 打开新窗口
      javaScriptCanOpenWindowsAutomatically = true
      // 加载来自任何其他来源的内容，即使该来源不安全
      mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
      // 设置可以访问文件
      allowFileAccess = true
      // 设置内置的缩放控件，若为 false，则该 WebView 不可缩放
      builtInZoomControls = true
      // 隐藏原生的缩放控件
      displayZoomControls = false
      // 将图片调整到适合 webView 的大小
      useWideViewPort = true
      // 缩放至屏幕的大小
      loadWithOverviewMode = true
      // h5 存储数据
      domStorageEnabled = true
      // 设置默认字体大小
      defaultFontSize = 18
    }
    webViewClient = object : WebViewClientCompat() {
      override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
        handler.proceed()
      }

      override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        return if (URLUtil.isValidUrl(url)) {
          if (URLUtil.isNetworkUrl(url)) {
            false
          } else {
            onEventListener?.onInterceptUri(Uri.parse(url))
            true
          }
        } else false
      }

      override fun onPageFinished(view: WebView, url: String) {
        onEventListener?.loadFinish()
      }
    }
    webChromeClient = object : WebChromeClient() {
      override fun onProgressChanged(view: WebView, newProgress: Int) {
        onEventListener?.onProgressChanged(newProgress)
      }

      override fun onReceivedTitle(view: WebView, title: String) {
        onEventListener?.onReceivedTitle(title)
      }

      override fun onShowFileChooser(
        view: WebView,
        filePathCallback: ValueCallback<Array<Uri>>,
        fileChooserParams: FileChooserParams
      ): Boolean {
        return onEventListener?.onShowFileChooser(filePathCallback, fileChooserParams)
          ?: super.onShowFileChooser(view, filePathCallback, fileChooserParams)
      }
    }
  }

  private fun attachToLifecycle() {
    val fragment = try {
      FragmentManager.findFragment<Fragment>(this)
    } catch (_: Exception) {
      null
    }
    if (fragment != null) {
      fragment.lifecycle.addObserver(lifecycleObserver)
    } else {
      var baseContext: Context? = context
      while (baseContext is ContextWrapper) {
        if (baseContext is Activity) break
        baseContext = baseContext.baseContext
      }
      (baseContext as? FragmentActivity)?.lifecycle?.addObserver(lifecycleObserver)
    }
  }

  private val lifecycleObserver = object : DefaultLifecycleObserver {
    override fun onResume(owner: LifecycleOwner) {
      onResume()
    }

    override fun onPause(owner: LifecycleOwner) {
      onPause()
    }

    override fun onDestroy(owner: LifecycleOwner) {
      onDestroy()
    }
  }

  interface OnEventListener {
    fun onInterceptUri(uri: Uri)

    fun onReceivedTitle(title: String)

    fun onShowFileChooser(
      filePathCallback: ValueCallback<Array<Uri>>,
      fileChooserParams: WebChromeClient.FileChooserParams
    ): Boolean

    fun onProgressChanged(i: Int)

    fun loadFinish()
  }
}
