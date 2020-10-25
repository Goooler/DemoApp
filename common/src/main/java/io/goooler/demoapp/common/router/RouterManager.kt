package io.goooler.demoapp.common.router

import android.content.Intent
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter

@Suppress("unused", "MemberVisibilityCanBePrivate")
object RouterManager {
    const val TARGET = "target"
    const val PARAMS = "params"
    const val RE_LOGIN = "reLogin"

    fun goLogin(isReLogin: Boolean) {
        val action = if (isReLogin) RE_LOGIN else null
        buildPostcard(RouterPath.LOGIN)
            .withAction(action)
            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            .navigation()
    }

    fun goMain() {
        buildPostcard(RouterPath.MAIN)
            .navigation()
    }

    fun goWeb(url: String) {
        buildPostcard(RouterPath.WEB)
            .withString(PARAMS, url)
            .navigation()
    }

    private fun buildPostcard(@RouterPath path: String): Postcard {
        return ARouter.getInstance()
            .build(path)
    }
}