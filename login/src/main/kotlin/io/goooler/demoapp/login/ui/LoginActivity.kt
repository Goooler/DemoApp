package io.goooler.demoapp.login.ui

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import io.goooler.demoapp.common.base.binding.BaseBindingActivity
import io.goooler.demoapp.common.router.RouterManager
import io.goooler.demoapp.common.router.RouterPath
import io.goooler.demoapp.login.databinding.LoginActivityBinding

@Route(path = RouterPath.LOGIN)
class LoginActivity : BaseBindingActivity<LoginActivityBinding>() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    if (intent.action != RouterManager.RE_LOGIN) {
      RouterManager.goMain()
      finish()
    }
  }
}
