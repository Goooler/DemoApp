package io.goooler.demoapp.common.service

import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.core.content.getSystemService
import io.goooler.demoapp.base.core.BaseService
import io.goooler.demoapp.base.util.unsafeLazy

class AudioPlayService : BaseService() {

  private val mediaPlayer = MediaPlayer()
  private var lastStreamUrl = ""
  private var audioManager: AudioManager? = null

  override val contentTitle: String = "正在播放音频"

  override fun onCreate() {
    super.onCreate()
    audioManager = getSystemService<AudioManager>()?.also {
      // 创建时监听音频焦点
      it.requestAudioFocus(
        audioFocusChangeListener,
        AudioManager.STREAM_MUSIC,
        AudioManager.AUDIOFOCUS_GAIN
      )
    }
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    when (intent?.action) {
      PAUSE -> pausePlay()
      RESUME -> resumePlay()
    }
    intent?.getStringExtra(STREAM_URL)?.let {
      if (it.isNotEmpty() && it != lastStreamUrl) {
        lastStreamUrl = it
        mediaPlayer.run {
          reset()
          val audioAttr = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()
          setAudioAttributes(audioAttr)
          setDataSource(it)
          prepareAsync()
          setOnPreparedListener {
            start()
          }
        }
      }
    }
    return super.onStartCommand(intent, flags, startId)
  }

  override fun onDestroy() {
    stopPlay()
    super.onDestroy()
  }

  private fun pausePlay() {
    if (mediaPlayer.isPlaying) mediaPlayer.pause()
  }

  private fun resumePlay() {
    if (mediaPlayer.isPlaying.not()) mediaPlayer.start()
  }

  private fun stopPlay() {
    // 停止后注销监听
    audioManager?.abandonAudioFocus(audioFocusChangeListener)
    mediaPlayer.stop()
  }

  private val audioFocusChangeListener by unsafeLazy {
    AudioManager.OnAudioFocusChangeListener {
      when (it) {
        AudioManager.AUDIOFOCUS_GAIN -> {
          resumePlay()
        }
        AudioManager.AUDIOFOCUS_LOSS -> {
          pausePlay()
        }
        AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
          pausePlay()
        }
      }
    }
  }

  companion object {
    const val STREAM_URL = "streamUrl"
    const val RESUME = "resume"
    const val PAUSE = "pause"

    fun pausePlay(context: Context) {
      val intent = Intent(context, AudioPlayService::class.java)
        .setAction(PAUSE)
      context.startService(intent)
    }

    fun resumePlay(context: Context) {
      val intent = Intent(context, AudioPlayService::class.java)
        .setAction(RESUME)
      context.startService(intent)
    }
  }
}
