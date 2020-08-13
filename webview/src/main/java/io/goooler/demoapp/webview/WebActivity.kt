package io.goooler.demoapp.webview

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.BarUtils
import io.goooler.demoapp.base.core.BaseActivity
import io.goooler.demoapp.common.router.RouterManager
import io.goooler.demoapp.common.router.RouterPath
import io.goooler.demoapp.webview.databinding.WebActivityBinding

@Route(path = RouterPath.WEB)
class WebActivity : BaseActivity() {
    private val binding by binding<WebActivityBinding>(R.layout.web_activity)

    private lateinit var webFragment: WebFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BarUtils.setStatusBarLightMode(this, true)
        binding.layoutTitle.listener = listener
        intent.extras?.getString(RouterManager.PARAMS)?.let {
            webFragment = WebFragment.newInstance(it)
            webFragment.eventListener = listener
            addFragment(R.id.fragment_container, webFragment)
        }
    }

    override fun onBackPressed() {
        webFragment.goBack()
    }

    private val listener = object : View.OnClickListener, WebFragment.EventListener {
        override fun onClick(v: View?) {
            when (v) {
                binding.layoutTitle.ivLeft -> {
                    finish()
                }
                binding.layoutTitle.ivRight -> {
                    val intent = Intent().setAction(Intent.ACTION_SEND)
                        .putExtra(Intent.EXTRA_TEXT, webFragment.url)
                        .setType("text/plain")
                    startActivity(Intent.createChooser(intent, null))
                }
            }
        }

        override fun onReceivedTitle(title: String) {
            binding.layoutTitle.tvCenter.text = title
        }

        override fun onProgressChanged(i: Int) {
            binding.progressBar.visibility = if (i >= 100) {
                View.GONE
            } else {
                binding.progressBar.progress = i
                View.VISIBLE
            }
        }
    }
}