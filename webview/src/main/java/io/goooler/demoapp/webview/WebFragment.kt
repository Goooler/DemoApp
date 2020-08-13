package io.goooler.demoapp.webview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import io.goooler.demoapp.base.core.BaseFragment
import io.goooler.demoapp.base.util.putArguments
import io.goooler.demoapp.base.util.unsafeLazy
import io.goooler.demoapp.webview.databinding.WebFragmentBinding

class WebFragment : BaseFragment() {

    private val binding by unsafeLazy {
        WebFragmentBinding.inflate(layoutInflater)
    }

    private val initOnce by unsafeLazy {
        binding.lifecycleOwner = this
        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(webView: WebView, i: Int) {
                super.onProgressChanged(webView, i)
                eventListener?.onProgressChanged(i)
            }

            override fun onReceivedTitle(webView: WebView, title: String) {
                super.onReceivedTitle(webView, title)
                eventListener?.onReceivedTitle(title)
            }
        }
    }

    var eventListener: EventListener? = null

    val url: String get() = binding.webView.url

    fun goBack() {
        if (binding.webView.canGoBack()) binding.webView.goBack() else finish()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initOnce
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.getString(URL)?.let {
            binding.webView.loadUrl(it)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.webView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.webView.onPause()
    }

    override fun onDestroyView() {
        binding.webView.onDestroy()
        super.onDestroyView()
    }

    interface EventListener {
        fun onReceivedTitle(title: String)
        fun onProgressChanged(i: Int)
    }

    companion object {
        private const val URL = "url"

        fun newInstance(url: String) = WebFragment().putArguments(
            bundleOf(
                URL to url
            )
        )
    }
}