package io.goooler.demoapp.web

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import com.blankj.utilcode.util.BarUtils
import io.goooler.demoapp.base.util.addFragment
import io.goooler.demoapp.common.base.binding.BaseBindingActivity
import io.goooler.demoapp.common.router.RouterManager
import io.goooler.demoapp.common.util.getColor
import io.goooler.demoapp.web.databinding.WebActivityBinding

class WebActivity : BaseBindingActivity<WebActivityBinding>() {

  private var webFragment: WebFragment? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    BarUtils.setStatusBarLightMode(this, true)
    binding.listener = listener
    intent.extras?.getString(RouterManager.PARAMS)?.let { url ->
      if (intent.action == RouterManager.USE_CHROME) {
        val colorSchemeParams = CustomTabColorSchemeParams.Builder()
          .setToolbarColor(io.goooler.demoapp.common.R.color.common_3F9FE0.getColor())
          .build()
        CustomTabsIntent.Builder()
          .setDefaultColorSchemeParams(colorSchemeParams)
          .build()
          .launchUrl(this, url.toUri())
        finish()
      } else {
        webFragment = WebFragment(url).also {
          it.onEventListener = listener
          addFragment(it, R.id.fragment_container)
        }
      }
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
      @Suppress("MagicNumber")
      binding.progressBar.visibility = if (i >= 100) {
        View.GONE
      } else {
        binding.progressBar.progress = i
        View.VISIBLE
      }
    }
  }
}
