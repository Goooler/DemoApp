package io.goooler.demoapp

import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk
import io.goooler.demoapp.common.RouterApplication
import io.goooler.demoapp.common.util.isFirstRun

@Suppress("unused")
class DemoApplication : RouterApplication() {

    override fun initRight() {
        super.initRight()
        QbSdk.initX5Environment(this, null)
    }

    override fun initLater() {
        super.initLater()
        if (isFirstRun) {
            isFirstRun = false
            QbSdk.initTbsSettings(
                mapOf(
                    TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER to true,
                    TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE to true
                )
            )
        }
    }
}