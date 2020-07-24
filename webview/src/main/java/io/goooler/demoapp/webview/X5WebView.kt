package io.goooler.demoapp.webview

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

class X5WebView(context: Context, attrs: AttributeSet?) : WebView(context, attrs) {

    constructor(context: Context) : this(context, null)

    init {
        view.isClickable = true
        initWebViewSettings()
        webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                url: String
            ): Boolean {
                // 防止加载网页时调起系统浏览器
                view.loadUrl(url)
                return true
            }

            override fun onPageFinished(
                webView: WebView,
                s: String
            ) {
                webView.loadUrl("javascript:App.resize(document.body.getBoundingClientRect().height)")
                super.onPageFinished(webView, s)
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebViewSettings() {
        settings.run {
            // 如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
            javaScriptEnabled = true
            // 支持通过JS打开新窗口
            javaScriptCanOpenWindowsAutomatically = true
            // 设置可以访问文件
            allowFileAccess = true
            // 支持缩放，默认为true。是下面那个的前提
            setSupportZoom(true)
            // 设置内置的缩放控件。若为false，则该WebView不可缩放
            builtInZoomControls = true
            // 隐藏原生的缩放控件
            displayZoomControls = false
            // 将图片调整到适合webView的大小
            useWideViewPort = true
            setSupportMultipleWindows(true)
            // 缩放至屏幕的大小
            loadWithOverviewMode = true
            setAppCacheEnabled(true)
            domStorageEnabled = true
            setGeolocationEnabled(true)
            setAppCacheMaxSize(Long.MAX_VALUE)
            pluginState = WebSettings.PluginState.ON_DEMAND
            // 设置缓存模式,一共有四种模式
            cacheMode = android.webkit.WebSettings.LOAD_NO_CACHE
            // 支持内容重新布局,一共有四种方式
            layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            // 设置默认字体大小
            defaultFontSize = 18
        }
    }

    fun onDestroy() {
        webChromeClient = null
        webViewClient = null
        destroy()
    }
}