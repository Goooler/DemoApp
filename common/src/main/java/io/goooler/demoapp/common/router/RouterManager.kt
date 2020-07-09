package io.goooler.demoapp.common.router

import android.content.Intent
import com.alibaba.android.arouter.launcher.ARouter
import io.goooler.demoapp.base.util.toJson

object RouterManager {
    const val TARGET = "target"
    const val PARAMS = "params"

    fun goLogin(@RouterPath target: String, params: Any) {
        ARouter.getInstance().build(RouterPath.LOGIN)
            .withString(TARGET, target)
            .withString(PARAMS, params.toJson())
            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            .navigation()
    }

    fun goMain() {
        ARouter.getInstance().build(RouterPath.MAIN)
            .navigation()
    }

    fun goWeb(url: String) {
        ARouter.getInstance().build(RouterPath.WEB)
            .withString(PARAMS, url)
            .navigation()
    }
}