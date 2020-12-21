package io.goooler.demoapp.webview

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import io.goooler.demoapp.base.util.putArguments
import io.goooler.demoapp.common.base.BaseThemeFragment
import io.goooler.demoapp.webview.databinding.WebFragmentBinding

class WebFragment private constructor() :
  BaseThemeFragment<WebFragmentBinding>(R.layout.web_fragment) {

  var onEventListener: OnEventListener? = null

  val url: String? get() = binding.webView.url

  val canGoBack: Boolean get() = binding.webView.canGoBack()

  fun goBack() {
    binding.webView.goBack()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View = binding.also {
    it.webView.onEventListener = listener
    it.webView.addJavascriptInterface(listener, "android")
    arguments?.getString(URL)?.let { url ->
      it.webView.loadUrl(url)
    }
  }.root

  private val listener = object : CompatWebView.OnEventListener, JsInterface {
    override fun onInterceptUri(uri: Uri) {
      startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    override fun onReceivedTitle(title: String) {
      onEventListener?.onReceivedTitle(title)
    }

    override fun onProgressChanged(i: Int) {
      onEventListener?.onProgressChanged(i)
    }

    override fun loadFinish() = Unit

    @JavascriptInterface
    override fun setTitle(title: String) {
      onEventListener?.onReceivedTitle(title)
    }
  }

  interface OnEventListener {
    fun onReceivedTitle(title: String)
    fun onProgressChanged(i: Int)
  }

  companion object {
    private const val URL = "url"

    fun newInstance(url: String): WebFragment = WebFragment().putArguments(
      URL to url
    )
  }
}
