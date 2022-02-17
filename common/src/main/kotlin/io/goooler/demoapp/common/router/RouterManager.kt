package io.goooler.demoapp.common.router

import android.content.Context

interface RouterManager {

  fun go(context: Context, url: String)

  fun goLogin(context: Context, isReLogin: Boolean)

  fun goMain(context: Context)

  fun goAudioPlay(context: Context)

  fun goWeb(context: Context, url: String, useChrome: Boolean = false)

  companion object : RouterManager {
    const val PARAMS = "params"
    const val RE_LOGIN = "reLogin"
    const val USE_CHROME = "useChrome"

    lateinit var impl: RouterManager

    override fun go(context: Context, url: String) {
      impl.go(context, url)
    }

    override fun goLogin(context: Context, isReLogin: Boolean) {
      impl.goLogin(context, isReLogin)
    }

    override fun goMain(context: Context) {
      impl.goMain(context)
    }

    override fun goAudioPlay(context: Context) {
      impl.goAudioPlay(context)
    }

    override fun goWeb(context: Context, url: String, useChrome: Boolean) {
      impl.goWeb(context, url, useChrome)
    }
  }
}
