package io.goooler.demoapp.base.core

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import androidx.lifecycle.LifecycleService

abstract class BaseService : LifecycleService() {

  /**
   * 必须不为 0
   */
  protected open val channelId: Int = 1

  protected open val contentTitle: String = ""

  protected open val channelName: String get() = getString(applicationInfo.labelRes)

  protected open val smallIcon: Int get() = applicationInfo.icon

  protected open val notification: Notification get() = createNormalNotification(contentTitle)

  override fun onCreate() {
    super.onCreate()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val channel = NotificationChannel(
        channelId.toString(),
        channelName,
        NotificationManager.IMPORTANCE_MIN
      )
      getSystemService<NotificationManager>()?.createNotificationChannel(channel)
      startForeground(channelId, notification)
    }
  }

  protected open fun createNormalNotification(content: String?): Notification {
    return NotificationCompat.Builder(this, channelId.toString()).also {
      if (content.isNullOrEmpty()) {
        it.setNotificationSilent()
      } else {
        it.setContentTitle(applicationInfo.name)
          .setContentTitle(content)
          .setWhen(System.currentTimeMillis())
          .setSmallIcon(smallIcon)
      }
    }.build()
  }
}
