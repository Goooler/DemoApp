package io.goooler.demoapp.webview

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.BarUtils
import io.goooler.demoapp.base.util.addFragment
import io.goooler.demoapp.common.base.BaseThemeActivity
import io.goooler.demoapp.common.router.RouterManager
import io.goooler.demoapp.common.router.RouterPath
import io.goooler.demoapp.webview.databinding.WebActivityBinding

@Route(path = RouterPath.WEB)
class WebActivity : BaseThemeActivity<WebActivityBinding>(R.layout.web_activity) {

  private var webFragment: WebFragment? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    BarUtils.setStatusBarLightMode(this, true)
    binding.listener = listener
    intent.extras?.getString(RouterManager.PARAMS)?.let { url ->
      if (intent.action == RouterManager.USE_CHROME) {
        CustomTabsIntent.Builder()
          .build()
          .launchUrl(this, url.toUri())
        finish()
      } else {
        webFragment = WebFragment.newInstance(url).also {
          it.onEventListener = listener
          supportFragmentManager.addFragment(R.id.fragment_container, it)
        }
      }
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
    override fun onClick(v: View) {
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
