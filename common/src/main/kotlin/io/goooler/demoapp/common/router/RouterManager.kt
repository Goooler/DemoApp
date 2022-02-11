package io.goooler.demoapp.common.router

import javax.inject.Inject

interface RouterManager {

  fun go(url: String)

  fun goLogin(isReLogin: Boolean)

  fun goMain()

  fun goAudioPlay()

  fun goWeb(url: String, useChrome: Boolean = false)

  companion object : RouterManager {
    const val PARAMS = "params"
    const val RE_LOGIN = "reLogin"
    const val USE_CHROME = "useChrome"

    @Inject
    lateinit var impl: RouterManager

    override fun go(url: String) {
      impl.go(url)
    }

    override fun goLogin(isReLogin: Boolean) {
      impl.goLogin(isReLogin)
    }

    override fun goMain() {
      impl.goMain()
    }

    override fun goAudioPlay() {
      impl.goAudioPlay()
    }

    override fun goWeb(url: String, useChrome: Boolean) {
      impl.goWeb(url, useChrome)
    }
  }
}
