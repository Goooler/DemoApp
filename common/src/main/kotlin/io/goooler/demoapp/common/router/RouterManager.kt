package io.goooler.demoapp.common.router

import android.content.Intent
import android.net.Uri
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter

@Suppress("unused", "MemberVisibilityCanBePrivate")
object RouterManager {
    const val TARGET = "target"
    const val PARAMS = "params"
    const val RE_LOGIN = "reLogin"

    fun go(url: String) {
        val uri = Uri.parse(url)
        if (uri.scheme == "demo") {
            val paths = uri.path?.split("/")
            when (paths?.firstOrNull()) {
                RouterPath.main -> {
                }
                RouterPath.web -> {
                }
                else -> {
                }
            }
        }
    }

    fun goLogin(isReLogin: Boolean) {
        val action = if (isReLogin) RE_LOGIN else null
        buildPostcard(RouterPath.login)
            .withAction(action)
            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            .navigation()
    }

    fun goMain() {
        buildPostcard(RouterPath.main)
            .navigation()
    }

    fun goWeb(url: String) {
        buildPostcard(RouterPath.web)
            .withString(PARAMS, url)
            .navigation()
    }

    private fun buildPostcard(path: String): Postcard {
        return ARouter.getInstance()
            .build(path)
    }
}