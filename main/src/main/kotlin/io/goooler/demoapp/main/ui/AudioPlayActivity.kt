package io.goooler.demoapp.main.ui

import android.os.Bundle
import android.view.View
import io.goooler.demoapp.common.base.binding.BaseBindingActivity
import io.goooler.demoapp.common.router.RouterManager
import io.goooler.demoapp.common.service.AudioPlayService
import io.goooler.demoapp.common.util.getString
import io.goooler.demoapp.main.R
import io.goooler.demoapp.main.databinding.MainAudioPlayActivityBinding

class AudioPlayActivity : BaseBindingActivity<MainAudioPlayActivityBinding>() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding.listener = listener
  }

  private val listener = View.OnClickListener {
    when (it) {
      binding.btStart -> {
        R.string.main_music_01_url.getString()?.let { url ->
          AudioPlayService.startPlay(this, url)
        }
      }
      binding.btPause -> {
        AudioPlayService.pausePlay(this)
      }
      binding.btResume -> {
        AudioPlayService.resumePlay(this)
      }
      binding.btStop -> {
        AudioPlayService.stopPlay(this)
      }
      binding.btGoMain -> {
        RouterManager.goMain(this)
      }
    }
  }
}
