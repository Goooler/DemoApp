package io.goooler.demoapp

import dagger.hilt.android.HiltAndroidApp
import io.goooler.demoapp.common.CommonApplication
import io.goooler.demoapp.common.router.RouterManager

@HiltAndroidApp
class DemoApplication : CommonApplication() {

  override fun initImmediately() {
    super.initImmediately()
    RouterManager.impl = RouterManagerImpl
  }
}
