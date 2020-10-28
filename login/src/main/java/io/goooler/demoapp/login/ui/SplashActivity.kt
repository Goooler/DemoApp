package io.goooler.demoapp.login.ui

import android.os.Bundle
import io.goooler.demoapp.base.core.BaseActivity
import io.goooler.demoapp.common.router.RouterManager

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RouterManager.goMain()
        overridePendingTransition(0, 0)
    }
}