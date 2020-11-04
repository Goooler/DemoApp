package io.goooler.demoapp.login.ui

import android.os.Bundle
import io.goooler.demoapp.common.base.BaseThemeActivity
import io.goooler.demoapp.common.router.RouterManager

class SplashActivity : BaseThemeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RouterManager.goMain()
        overridePendingTransition(0, 0)
    }
}