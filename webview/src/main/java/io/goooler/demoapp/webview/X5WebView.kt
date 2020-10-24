package io.goooler.demoapp.webview

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.webkit.URLUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import io.goooler.demoapp.base.util.ViewUtil

@Suppress("MemberVisibilityCanBePrivate")
class X5WebView(context: Context, attrs: AttributeSet? = null) : WebView(context, attrs),
    DefaultLifecycleObserver {

    var onEventListener: OnEventListener? = null

    init {
        initWebViewSettings()
    }

    fun onDestroy() {
        stopLoading()
        destroy()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        attachToLifecycle()
    }

    override fun onResume(owner: LifecycleOwner) {
        onResume()
    }

    override fun onPause(owner: LifecycleOwner) {
        onPause()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        onDestroy()
    }

    private fun attachToLifecycle() {
        (context as? FragmentActivity)?.let {
            val fragment = ViewUtil.findSupportFragment(this, it)
            if (fragment != null) {
                fragment.lifecycle.addObserver(this)
            } else {
                it.lifecycle.addObserver(this)
            }
        }
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
            // 设置内置的缩放控件，若为 false，则该 WebView 不可缩放
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
                if (URLUtil.isNetworkUrl(url)) {
                    view.loadUrl(url)
                } else {
                    onEventListener?.onInterceptUri(Uri.parse(url))
                }
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
        webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(webView: WebView, i: Int) {
                super.onProgressChanged(webView, i)
                onEventListener?.onProgressChanged(i)
            }

            override fun onReceivedTitle(webView: WebView, title: String) {
                super.onReceivedTitle(webView, title)
                onEventListener?.onReceivedTitle(title)
            }
        }
    }

    interface OnEventListener {
        fun onInterceptUri(uri: Uri)
        fun onReceivedTitle(title: String)
        fun onProgressChanged(i: Int)
    }
}