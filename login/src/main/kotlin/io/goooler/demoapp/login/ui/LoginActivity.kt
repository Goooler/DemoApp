package io.goooler.demoapp.login.ui

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import io.goooler.demoapp.common.base.BaseThemeActivity
import io.goooler.demoapp.common.router.RouterManager
import io.goooler.demoapp.common.router.RouterPath
import io.goooler.demoapp.login.R
import io.goooler.demoapp.login.databinding.LoginActivityBinding

@Route(path = RouterPath.LOGIN)
class LoginActivity : BaseThemeActivity<LoginActivityBinding>(R.layout.login_activity) {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    if (intent.action != RouterManager.RE_LOGIN) {
      RouterManager.goMain()
      finish()
    }
  }
}
