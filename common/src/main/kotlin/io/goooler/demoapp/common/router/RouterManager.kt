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
  const val USE_CHROME = "useChrome"

  fun go(url: String) {
    val uri = Uri.parse(url)
    if (uri.scheme == "demo") {
      val paths = uri.path?.split("/")
      when (paths?.firstOrNull()) {
        RouterPath.MAIN -> {
        }
        RouterPath.WEB -> {
        }
        else -> {
        }
      }
    }
  }

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

  fun goWidget() {
    buildPostcard(RouterPath.WIDGET)
      .navigation()
  }

  fun goWeb(url: String, useChrome: Boolean = false) {
    val action = if (useChrome) USE_CHROME else null
    buildPostcard(RouterPath.WEB)
      .withAction(action)
      .withString(PARAMS, url)
      .navigation()
  }

  private fun buildPostcard(path: String): Postcard {
    return ARouter.getInstance()
      .build(path)
  }
}
