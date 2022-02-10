package io.goooler.demoapp.web

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.BarUtils
import io.goooler.demoapp.base.util.addFragment
import io.goooler.demoapp.base.util.replaceFragment
import io.goooler.demoapp.common.base.binding.BaseBindingActivity
import io.goooler.demoapp.common.router.RouterPath
import io.goooler.demoapp.web.databinding.WebActivityBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Route(path = RouterPath.WEB)
class WebActivity : BaseBindingActivity<WebActivityBinding>() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    BarUtils.setStatusBarLightMode(this, true)

    val blankFragment = BlankFragment()

    addFragment(blankFragment, R.id.fragment_container)

    lifecycleScope.launch(Dispatchers.IO) {
      delay(1000)
      withContext(Dispatchers.Main) {
        replaceFragment(WebFragment("bilibili.com"), R.id.fragment_container, true)
      }
    }
  }
}
