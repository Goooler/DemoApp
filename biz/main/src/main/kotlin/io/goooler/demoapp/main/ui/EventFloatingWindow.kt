package io.goooler.demoapp.main.ui

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.imuxuan.floatingview.FloatingView

object EventFloatingWindow {
  fun show(activity: Activity) {
    val app = activity.applicationContext as Application
    app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
      override fun onActivityStarted(activity: Activity) {
        FloatingView.get().attach(activity)
      }

      override fun onActivityStopped(activity: Activity) {
        FloatingView.get().detach(activity)
      }

      override fun onActivityPaused(activity: Activity) = Unit

      override fun onActivityDestroyed(activity: Activity) = Unit

      override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit

      override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) = Unit

      override fun onActivityResumed(activity: Activity) = Unit
    })
    FloatingView.get().add()
    FloatingView.get().attach(activity)
  }
}
