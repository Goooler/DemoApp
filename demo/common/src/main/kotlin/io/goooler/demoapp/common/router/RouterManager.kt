package io.goooler.demoapp.common.router

import android.content.Context

interface RouterManager {

  fun go(context: Context, url: String)

  fun goLogin(context: Context, isReLogin: Boolean)

  fun goMain(context: Context)

  fun goRepoDetail(context: Context, fullName: String)

  fun goAudioPlay(context: Context)

  fun goWeb(context: Context, url: String, useChrome: Boolean = false)

  companion object : RouterManager by routerManagerDelegate {
    const val PARAMS = "params"
    const val RE_LOGIN = "reLogin"
    const val USE_CHROME = "useChrome"
  }
}

lateinit var routerManagerDelegate: RouterManager
