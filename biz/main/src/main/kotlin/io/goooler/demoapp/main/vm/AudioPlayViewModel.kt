package io.goooler.demoapp.main.vm

import io.goooler.demoapp.common.base.theme.BaseThemeViewModel
import io.goooler.demoapp.common.util.getString
import io.goooler.demoapp.main.R

class AudioPlayViewModel : BaseThemeViewModel() {

  val audioUrl: String get() = R.string.main_music_01_url.getString().orEmpty()
}
