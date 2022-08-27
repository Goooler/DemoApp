package io.goooler.demoapp.login.ui

import android.os.Build
import android.os.Bundle
import io.goooler.demoapp.base.core.BaseActivity
import io.goooler.demoapp.common.router.RouterManager

class SplashActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    RouterManager.goMain(this)
    overridePendingTransition(0, 0)
    finish()
  }

  override fun onBackPressed() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      finish()
    } else {
      super.onBackPressed()
    }
  }
}
