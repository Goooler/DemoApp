@file:Suppress("unused")

package io.goooler.demoapp.base.util

import android.app.Service
import android.content.Context
import android.media.AudioManager
import android.os.Build

@Suppress("DEPRECATION")
object AudioUtil {

  /**
   * 音频静音
   */
  fun setMusicMute(context: Context) {
    setMusicMute(context, true)
  }

  /**
   * 取消音频静音
   */
  fun setMusicUnmute(context: Context) {
    setMusicMute(context, false)
  }

  /**
   * 设置音频静音
   *
   * @param context .
   * @param mute 是否静音
   */
  private fun setMusicMute(context: Context, mute: Boolean) {
    (context.getSystemService(Service.AUDIO_SERVICE) as? AudioManager)?.let {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val direction = if (mute) AudioManager.ADJUST_UNMUTE else AudioManager.ADJUST_MUTE
        it.adjustStreamVolume(AudioManager.STREAM_MUSIC, direction, 0)
      } else {
        it.setStreamMute(AudioManager.STREAM_MUSIC, mute)
      }
    }
  }
}
