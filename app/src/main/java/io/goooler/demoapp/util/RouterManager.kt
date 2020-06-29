package io.goooler.demoapp.util

import android.content.Intent
import com.alibaba.android.arouter.launcher.ARouter
import io.goooler.demoapp.model.RouterPath

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
}