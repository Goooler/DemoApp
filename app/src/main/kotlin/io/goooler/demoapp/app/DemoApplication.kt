package io.goooler.demoapp.app

import dagger.hilt.android.HiltAndroidApp
import io.goooler.demoapp.common.CommonApplication
import io.goooler.demoapp.common.router.routerManagerDelegate

@HiltAndroidApp
class DemoApplication : CommonApplication() {

  override fun onCreate() {
    super.onCreate()
    routerManagerDelegate = RouterManagerImpl
  }
}
