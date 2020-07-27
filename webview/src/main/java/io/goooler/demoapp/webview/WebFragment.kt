package io.goooler.demoapp.webview

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import io.goooler.demoapp.base.core.BaseFragment
import io.goooler.demoapp.base.util.device.StatusBarUtil
import io.goooler.demoapp.base.util.unsafeLazy
import io.goooler.demoapp.webview.databinding.WebFragmentBinding

class WebFragment : BaseFragment() {

    private val binding by unsafeLazy {
        WebFragmentBinding.inflate(layoutInflater)
    }

    private val initOnce by unsafeLazy {
        binding.lifecycleOwner = this
        binding.layoutTitle.listener = listener
        binding.webView.webChromeClient = listener
    }

    fun goBack() {
        if (binding.webView.canGoBack()) binding.webView.goBack() else finish()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        StatusBarUtil.setStatusBarColorBlack(requireActivity())
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

    override fun onDestroy() {
        super.onDestroy()
        binding.webView.onDestroy()
    }

    private val listener = object : View.OnClickListener, WebChromeClient() {
        override fun onClick(v: View?) {
            when (v) {
                binding.layoutTitle.ivLeft -> {
                    finish()
                }
                binding.layoutTitle.ivRight -> {
                    val intent = Intent().setAction(Intent.ACTION_SEND)
                        .putExtra(Intent.EXTRA_TEXT, binding.webView.url)
                        .setType("text/plain")
                    startActivity(Intent.createChooser(intent, ""))
                }
            }
        }

        override fun onProgressChanged(webView: WebView?, i: Int) {
            super.onProgressChanged(webView, i)
            binding.webViewProgressBar.visibility = if (i >= 100) {
                View.GONE
            } else {
                binding.webViewProgressBar.progress = i
                View.VISIBLE
            }
        }

        override fun onReceivedTitle(webView: WebView, title: String?) {
            super.onReceivedTitle(webView, title)
            binding.layoutTitle.tvCenter.text = title.orEmpty()
        }
    }

    companion object {
        private const val URL = "url"

        fun newInstance(url: String) = WebFragment().apply {
            arguments = Bundle().apply {
                putString(URL, url)
            }
        }
    }
}