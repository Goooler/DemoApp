package io.goooler.demoapp

import com.tencent.smtt.sdk.QbSdk
import io.goooler.demoapp.common.RouterApplication

@Suppress("unused")
class DemoApplication : RouterApplication() {

    override fun initLater() {
        super.initLater()
        QbSdk.initX5Environment(this, null)
    }
}