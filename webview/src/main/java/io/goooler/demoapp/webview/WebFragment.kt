package io.goooler.demoapp.webview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.goooler.demoapp.base.core.BaseWebFragment
import io.goooler.demoapp.base.util.device.StatusBarUtil
import io.goooler.demoapp.base.util.unsafeLazy
import io.goooler.demoapp.webview.databinding.WebFragmentBinding

class WebFragment : BaseWebFragment() {
    private val binding by unsafeLazy {
        WebFragmentBinding.inflate(layoutInflater)
    }

    private val initOnce by unsafeLazy {
        binding.lifecycleOwner = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        StatusBarUtil.setStatusBarTransparent(requireActivity())
        initOnce
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.getString(URL)?.let {
            binding.webView.loadUrl(it)
        }
    }

    companion object {
        private const val URL = "url"

        fun newInstance(url: String): WebFragment {
            val args = Bundle()
            args.putString(URL, url)
            val fragment = WebFragment()
            fragment.arguments = args
            return fragment
        }
    }
}