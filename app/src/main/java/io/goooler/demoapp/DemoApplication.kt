package io.goooler.demoapp

import com.tencent.mmkv.MMKV
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk
import io.goooler.demoapp.base.util.CrashHandler
import io.goooler.demoapp.base.util.ObjectBox
import io.goooler.demoapp.common.RouterApplication
import io.goooler.demoapp.common.util.isFirstRun
import io.goooler.demoapp.main.bean.MyObjectBox


class DemoApplication : RouterApplication() {

    override fun initData() {
        super.initData()
        CrashHandler.init()
        QbSdk.initX5Environment(this, null)
        ObjectBox.init {
            MyObjectBox.builder()
                .androidContext(this)
                .build()
        }
        MMKV.initialize(this)
    }

    override fun initLater() {
        super.initLater()
        if (isFirstRun) {
            QbSdk.initTbsSettings(
                mapOf(
                    TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER to true,
                    TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE to true
                )
            )
        }
    }
}