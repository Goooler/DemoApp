package io.goooler.demoapp.common

import com.alibaba.android.arouter.launcher.ARouter
import io.goooler.demoapp.base.core.BaseApplication
import io.goooler.demoapp.base.util.debugRun

open class RouterApplication : BaseApplication() {

    override fun initData() {
        super.initData()
        ARouter.init(this)
    }

    override fun initLater() {
        super.initLater()
        debugRun {
            ARouter.openLog()
            ARouter.openDebug()
        }
    }
}