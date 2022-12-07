package io.goooler.demoapp.app

import io.goooler.demoapp.common.CommonApplication
import io.goooler.demoapp.common.router.routerManagerDelegate

class DemoApplication : CommonApplication() {

  override fun onCreate() {
    super.onCreate()
    routerManagerDelegate = RouterManagerImpl
  }
}
