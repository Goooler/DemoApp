package io.goooler.demoapp.app

import android.content.Context
import android.content.Intent
import io.goooler.demoapp.common.router.RouterManager
import io.goooler.demoapp.detail.ui.RepoDetailActivity
import io.goooler.demoapp.login.ui.LoginActivity
import io.goooler.demoapp.main.ui.AudioPlayActivity
import io.goooler.demoapp.main.ui.ActualMainActivity
import io.goooler.demoapp.web.WebActivity

object RouterManagerImpl : RouterManager {

  override fun go(context: Context, url: String) {
    TODO("Not yet implemented")
  }

  override fun goLogin(context: Context, isReLogin: Boolean) {
    Intent(context, LoginActivity::class.java)
      .setAction(if (isReLogin) RouterManager.RE_LOGIN else null)
      .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
      .let(context::startActivity)
  }

  override fun goMain(context: Context) {
    Intent(context, ActualMainActivity::class.java)
      .let(context::startActivity)
  }

  override fun goRepoDetail(context: Context, fullName: String) {
    Intent(context, RepoDetailActivity::class.java)
      .putExtra(RepoDetailActivity.FULL_NAME, fullName)
      .let(context::startActivity)
  }

  override fun goAudioPlay(context: Context) {
    Intent(context, AudioPlayActivity::class.java)
      .let(context::startActivity)
  }

  override fun goWeb(context: Context, url: String, useChrome: Boolean) {
    Intent(context, WebActivity::class.java)
      .putExtra(RouterManager.PARAMS, url)
      .setAction(if (useChrome) RouterManager.USE_CHROME else null)
      .let(context::startActivity)
  }
}
