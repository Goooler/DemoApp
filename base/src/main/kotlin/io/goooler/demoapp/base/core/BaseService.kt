package io.goooler.demoapp.base.core

import android.app.Notification
import android.app.NotificationManager
import androidx.annotation.DrawableRes
import androidx.annotation.IntRange
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LifecycleService

abstract class BaseService : LifecycleService() {

  /**
   * 必须不为 0
   */
  @IntRange(from = 1)
  protected open val channelId: Int = 1

  protected open val contentTitle: String get() = applicationInfo.name

  protected open val contentText: String? get() = null

  protected open val channelName: String get() = getString(applicationInfo.labelRes)

  @get:DrawableRes
  protected open val smallIcon: Int
    get() = applicationInfo.icon

  protected open val notification: Notification
    get() = NotificationCompat.Builder(this, channelId.toString())
      .setContentTitle(contentTitle)
      .setContentText(contentText)
      .setWhen(System.currentTimeMillis())
      .setSmallIcon(smallIcon)
      .build()

  protected open val channel: NotificationChannelCompat
    get() = NotificationChannelCompat.Builder(
      channelId.toString(),
      @Suppress("InlinedApi") NotificationManager.IMPORTANCE_MIN,
    ).setName(channelName).build()

  override fun onCreate() {
    super.onCreate()
    NotificationManagerCompat.from(this).createNotificationChannel(channel)
    startForeground(channelId, notification)
  }

  override fun onDestroy() {
    stopForeground(true)
    super.onDestroy()
  }
}
