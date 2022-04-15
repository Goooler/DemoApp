package io.goooler.demoapp.web

import android.content.Intent
import android.net.Uri
import android.webkit.JavascriptInterface
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.IntRange
import androidx.core.net.toUri
import io.goooler.demoapp.base.util.isNetworkUrl
import io.goooler.demoapp.base.util.putArguments
import io.goooler.demoapp.base.util.toMimeType
import io.goooler.demoapp.common.base.binding.BaseBindingFragment
import io.goooler.demoapp.web.databinding.WebFragmentBinding

class WebFragment : BaseBindingFragment<WebFragmentBinding>() {

  private lateinit var headers: Map<String, String>
  private var fileChooserCallback: ValueCallback<Array<Uri>>? = null
  private lateinit var fileChooserLauncher: ActivityResultLauncher<Intent>

  var onEventListener: OnEventListener? = null

  val url: String? get() = binding.webView.url

  fun goBack(): Boolean = binding.webView.canGoBack().also {
    if (it) binding.webView.goBack()
  }

  override fun initOnce() {
    headers = mapOf(
      "buildType" to BuildConfig.BUILD_TYPE,
      "flavor" to BuildConfig.FLAVOR
    )

    binding.let {
      it.webView.onEventListener = listener
      it.webView.addJavascriptInterface(listener, "android")
      arguments?.getString(URL)?.let { url ->
        it.webView.loadUrl(url, headers)
      }
    }

    fileChooserLauncher =
      registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        fileChooserCallback?.onReceiveValue(
          WebChromeClient.FileChooserParams.parseResult(it.resultCode, it.data)
        )
        fileChooserCallback = null
      }
  }

  private val listener = object : CompatWebView.OnEventListener, JsInterface {
    override fun onInterceptUrl(url: String): Boolean {
      return if (url.isNetworkUrl()) false else {
        startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
        true
      }
    }

    override fun onReceivedTitle(title: String) {
      onEventListener?.onReceivedTitle(title)
    }

    override fun onShowFileChooser(
      filePathCallback: ValueCallback<Array<Uri>>,
      fileChooserParams: WebChromeClient.FileChooserParams
    ): Boolean {
      fileChooserCallback = filePathCallback
      val canMultiSelect =
        fileChooserParams.mode == WebChromeClient.FileChooserParams.MODE_OPEN_MULTIPLE
      val mimeType = fileChooserParams.acceptTypes.firstOrNull()?.toMimeType() ?: "*/*"
      startFileChooser(canMultiSelect, mimeType)
      return true
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

  private fun startFileChooser(canMultiSelect: Boolean, mimeType: String) {
    val intent = Intent(Intent.ACTION_GET_CONTENT)
      .addCategory(Intent.CATEGORY_OPENABLE)
      .putExtra(Intent.EXTRA_ALLOW_MULTIPLE, canMultiSelect)
      .setType(mimeType)
    fileChooserLauncher.launch(intent)
  }

  interface OnEventListener {
    fun onReceivedTitle(title: String)
    fun onProgressChanged(@IntRange(from = 0) i: Int)
  }

  companion object {
    private const val URL = "url"

    operator fun invoke(url: String): WebFragment = WebFragment().putArguments(
      URL to url
    )
  }
}
