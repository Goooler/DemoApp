package io.goooler.demoapp.webview

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

class X5WebView(context: Context, attrs: AttributeSet? = null) : WebView(context, attrs) {

    init {
        initWebViewSettings()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebViewSettings() {
        view.isClickable = true
        settings.run {
            // 如果访问的页面中要与 Javascript 交互，则 webview 必须设置支持 Javascript
            javaScriptEnabled = true
            // 支持通过JS打开新窗口
            javaScriptCanOpenWindowsAutomatically = true
            // 设置可以访问文件
            allowFileAccess = true
            // 支持缩放，默认为 true，是下面那个的前提
            setSupportZoom(true)
            // 设置内置的缩放控件。若为 false，则该 WebView 不可缩放
            builtInZoomControls = true
            // 隐藏原生的缩放控件
            displayZoomControls = false
            // 将图片调整到适合 webView 的大小
            useWideViewPort = true
            setSupportMultipleWindows(true)
            // 缩放至屏幕的大小
            loadWithOverviewMode = true
            setAppCacheEnabled(true)
            domStorageEnabled = true
            setGeolocationEnabled(true)
            setAppCacheMaxSize(Long.MAX_VALUE)
            pluginState = WebSettings.PluginState.ON_DEMAND
            // 支持内容重新布局,一共有四种方式
            layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            // 设置默认字体大小
            defaultFontSize = 18
        }

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

    fun onDestroy() {
        stopLoading()
        destroy()
    }
}