package io.goooler.demoapp.main.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import io.goooler.demoapp.common.base.binding.BaseBindingActivity
import io.goooler.demoapp.common.router.RouterManager
import io.goooler.demoapp.common.service.AudioPlayService
import io.goooler.demoapp.main.databinding.MainAudioPlayActivityBinding
import io.goooler.demoapp.main.vm.AudioPlayViewModel

class AudioPlayActivity : BaseBindingActivity<MainAudioPlayActivityBinding>() {

  private val vm: AudioPlayViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding.listener = listener
  }

  private val listener = View.OnClickListener {
    when (it) {
      binding.btStart -> {
        AudioPlayService.startPlay(this, vm.audioUrl)
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
