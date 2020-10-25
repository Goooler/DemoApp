package io.goooler.demoapp.webview

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import io.goooler.demoapp.base.core.BaseFragment
import io.goooler.demoapp.base.util.putArguments
import io.goooler.demoapp.base.util.unsafeLazy
import io.goooler.demoapp.base.widget.CustomWebView
import io.goooler.demoapp.webview.databinding.WebFragmentBinding

class WebFragment private constructor() : BaseFragment() {

    private val binding by unsafeLazy {
        WebFragmentBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
            webView.onEventListener = listener
        }
    }

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
    ): View? {
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.getString(URL)?.let {
            binding.webView.loadUrl(it)
        }
    }

    private val listener = object : CustomWebView.OnEventListener {
        override fun onInterceptUri(uri: Uri) {
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }

        override fun onReceivedTitle(title: String) {
            onEventListener?.onReceivedTitle(title)
        }

        override fun onProgressChanged(i: Int) {
            onEventListener?.onProgressChanged(i)
        }
    }

    interface OnEventListener {
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