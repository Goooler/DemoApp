package io.goooler.demoapp.common.service

import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.core.content.getSystemService
import androidx.lifecycle.lifecycleScope
import io.goooler.demoapp.base.core.BaseService
import io.goooler.demoapp.base.util.unsafeLazy
import io.goooler.demoapp.common.databinding.CommonFloatingTipViewBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AudioPlayService : BaseService() {

  private val mediaPlayer = MediaPlayer()
  private var lastStreamUrl = ""
  private var audioManager: AudioManager? = null

  override val contentTitle: String get() = "正在播放音频"

  override fun onCreate() {
    super.onCreate()
    audioManager = getSystemService()
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    when (intent?.action) {
      PAUSE -> pausePlay()
      RESUME -> resumePlay()
    }
    intent?.getStringExtra(STREAM_URL)?.let {
      if (it.isNotEmpty() && it != lastStreamUrl) {
        lastStreamUrl = it
        startPlay(it)
      }
    }
    return super.onStartCommand(intent, flags, startId)
  }

  override fun onDestroy() {
    stopPlay()
    super.onDestroy()
  }

  private fun startPlay(source: String) {
    requestAudioFocus()
    mediaPlayer.run {
      reset()
      val audioAttr = AudioAttributes.Builder()
        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()
      setAudioAttributes(audioAttr)
      setDataSource(source)
      prepareAsync()
      setOnPreparedListener {
        start()
      }
    }
    lifecycleScope.launch {
      delay(3000)
      val floatingView = CommonFloatingTipViewBinding.inflate(LayoutInflater.from(this@AudioPlayService)).root
      val layoutParam = WindowManager.LayoutParams().apply {
        width = WindowManager.LayoutParams.WRAP_CONTENT
        height = WindowManager.LayoutParams.WRAP_CONTENT
      }
      getSystemService<WindowManager>()?.addView(floatingView, layoutParam)
    }
  }

  private fun resumePlay() {
    requestAudioFocus()
    if (mediaPlayer.isPlaying.not()) mediaPlayer.start()
  }

  private fun pausePlay() {
    abandonAudioFocus()
    if (mediaPlayer.isPlaying) mediaPlayer.pause()
  }

  private fun stopPlay() {
    abandonAudioFocus()
    audioManager = null
    mediaPlayer.stop()
  }

  private fun requestAudioFocus() {
    audioManager?.requestAudioFocus(
      audioFocusChangeListener,
      AudioManager.STREAM_MUSIC,
      AudioManager.AUDIOFOCUS_GAIN,
    )
  }

  private fun abandonAudioFocus() {
    audioManager?.abandonAudioFocus(audioFocusChangeListener)
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
    private const val STREAM_URL = "streamUrl"
    private const val RESUME = "resume"
    private const val PAUSE = "pause"

    fun startPlay(context: Context, url: String) {
      val intent = Intent(context, AudioPlayService::class.java).putExtra(STREAM_URL, url)
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        context.startForegroundService(intent)
      } else {
        context.startService(intent)
      }
    }

    fun resumePlay(context: Context) {
      val intent = Intent(context, AudioPlayService::class.java)
        .setAction(RESUME)
      context.startService(intent)
    }

    fun pausePlay(context: Context) {
      val intent = Intent(context, AudioPlayService::class.java)
        .setAction(PAUSE)
      context.startService(intent)
    }

    fun stopPlay(context: Context) {
      context.stopService(Intent(context, AudioPlayService::class.java))
    }
  }
}
