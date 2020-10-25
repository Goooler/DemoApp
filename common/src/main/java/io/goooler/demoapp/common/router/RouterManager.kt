package io.goooler.demoapp.common.router

import android.content.Intent
import com.alibaba.android.arouter.launcher.ARouter

@Suppress("unused", "MemberVisibilityCanBePrivate")
object RouterManager {
    const val TARGET = "target"
    const val PARAMS = "params"
    const val RE_LOGIN = "reLogin"

    fun goLogin(isReLogin: Boolean) {
        val action = if (isReLogin) RE_LOGIN else null
        ARouter.getInstance().build(RouterPath.LOGIN)
            .withAction(action)
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