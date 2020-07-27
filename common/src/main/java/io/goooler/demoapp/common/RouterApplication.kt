package io.goooler.demoapp.common

import com.alibaba.android.arouter.launcher.ARouter
import io.goooler.demoapp.base.core.BaseApplication
import io.goooler.demoapp.common.util.CrashHandler

open class RouterApplication : BaseApplication() {

    override fun initRight() {
        super.initRight()
        CrashHandler.init()
        ARouter.init(this)
    }
}