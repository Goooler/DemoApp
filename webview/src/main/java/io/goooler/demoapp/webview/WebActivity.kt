package io.goooler.demoapp.webview

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.BarUtils
import io.goooler.demoapp.base.core.BaseActivity
import io.goooler.demoapp.base.util.addFragment
import io.goooler.demoapp.base.util.unsafeLazy
import io.goooler.demoapp.common.router.RouterManager
import io.goooler.demoapp.common.router.RouterPath
import io.goooler.demoapp.webview.databinding.WebActivityBinding

@Route(path = RouterPath.WEB)
class WebActivity : BaseActivity() {
    private val binding by unsafeLazy { WebActivityBinding.inflate(layoutInflater) }

    private var webFragment: WebFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        BarUtils.setStatusBarLightMode(this, true)
        binding.layoutTitle.listener = listener
        intent.extras?.getString(RouterManager.PARAMS)?.let {
            webFragment = WebFragment.newInstance(it)
            webFragment!!.onEventListener = listener
            supportFragmentManager.addFragment(R.id.fragment_container, webFragment!!)
        }
    }

    override fun onBackPressed() {
        if (webFragment?.canGoBack == true) {
            webFragment?.goBack()
        } else {
            super.onBackPressed()
        }
    }

    private val listener = object : View.OnClickListener, WebFragment.OnEventListener {
        override fun onClick(v: View?) {
            when (v) {
                binding.layoutTitle.ivLeft -> {
                    finish()
                }
                binding.layoutTitle.ivRight -> {
                    val intent = Intent().setAction(Intent.ACTION_SEND)
                        .putExtra(Intent.EXTRA_TEXT, webFragment?.url)
                        .setType("text/plain")
                    startActivity(Intent.createChooser(intent, null))
                }
            }
        }

        override fun onReceivedTitle(title: String) {
            binding.layoutTitle.title = title
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