package io.goooler.demoapp.main.vm

import io.goooler.demoapp.base.core.BaseViewModel
import io.goooler.demoapp.common.util.getString
import io.goooler.demoapp.main.R

class AudioPlayViewModel : BaseViewModel() {

  val audioUrl: String get() = R.string.main_music_01_url.getString()
}
