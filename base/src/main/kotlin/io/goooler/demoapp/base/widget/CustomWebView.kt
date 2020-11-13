package io.goooler.demoapp.base.widget

import android.content.Context
import android.net.Uri
import android.net.http.SslError
import android.util.AttributeSet
import android.view.View
import android.webkit.*
import androidx.collection.ArrayMap
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

@Suppress("MemberVisibilityCanBePrivate", "SetJavaScriptEnabled", "JavascriptInterface")
class CustomWebView(context: Context, attrs: AttributeSet? = null) : WebView(context, attrs),
    DefaultLifecycleObserver {

    var onEventListener: OnEventListener? = null

    init {
        initWebViewSettings()
    }

    fun onDestroy() {
        stopLoading()
        destroy()
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

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        attachToLifecycle()
    }

    private fun initWebViewSettings() {
        settings.run {
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            javaScriptEnabled = true
            // 支持通过JS打开新窗口
            javaScriptCanOpenWindowsAutomatically = true
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
            domStorageEnabled = true
            // 设置默认字体大小
            defaultFontSize = 18
        }
        webViewClient = object : WebViewClient() {

            override fun onReceivedSslError(
                view: WebView,
                handler: SslErrorHandler,
                error: SslError
            ) {
                handler.proceed()
            }

            override fun shouldOverrideUrlLoading(
                view: WebView,
                url: String
            ): Boolean {
                if (!URLUtil.isNetworkUrl(url)) {
                    onEventListener?.onInterceptUri(Uri.parse(url))
                }
                return false
            }

            override fun onPageFinished(
                webView: WebView,
                s: String
            ) {
                super.onPageFinished(webView, s)
                onEventListener?.loadFinish()
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

    private fun attachToLifecycle() {
        fun findAllSupportFragmentsWithViews(
            topLevelFragments: Collection<Fragment>, result: MutableMap<View, Fragment>
        ) {
            topLevelFragments.forEach {
                it.view?.let { v ->
                    result[v] = it
                    findAllSupportFragmentsWithViews(it.childFragmentManager.fragments, result)
                }
            }
        }

        fun findSupportFragment(target: View, activity: FragmentActivity): Fragment? {
            val tempViewToSupportFragment = ArrayMap<View, Fragment>()
            findAllSupportFragmentsWithViews(
                activity.supportFragmentManager.fragments, tempViewToSupportFragment
            )
            var result: Fragment? = null
            val activityRoot = activity.findViewById<View>(android.R.id.content)
            var current = target
            while (current != activityRoot) {
                result = tempViewToSupportFragment[current]
                if (result != null) {
                    break
                }
                current = if (current.parent is View) {
                    current.parent as View
                } else {
                    break
                }
            }
            tempViewToSupportFragment.clear()
            return result
        }

        (context as? FragmentActivity)?.let {
            val fragment = findSupportFragment(this, it)
            if (fragment != null) {
                fragment.lifecycle.addObserver(this)
            } else {
                it.lifecycle.addObserver(this)
            }
        }
    }

    interface OnEventListener {
        fun onInterceptUri(uri: Uri)
        fun onReceivedTitle(title: String)
        fun onProgressChanged(i: Int)
        fun loadFinish()
    }
}