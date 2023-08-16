package io.goooler.demoapp.main.ui

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.imuxuan.floatingview.FloatingMagnetView
import com.imuxuan.floatingview.FloatingView
import io.goooler.demoapp.main.databinding.FloatingLayoutBinding
import io.goooler.demoapp.main.model.MainCommonVhModel
import io.goooler.demoapp.main.ui.adapter.MainSrlRvAdapter
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object EventFloatingWindow {
  private lateinit var rvAdapter: MainSrlRvAdapter

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

    rvAdapter = MainSrlRvAdapter(object : MainSrlRvAdapter.OnEventListener {})
    val binding = FloatingLayoutBinding.inflate(activity.layoutInflater).also {
      it.rvList.adapter = rvAdapter
    }
    FloatingView.get()
      .customView(binding.root as FloatingMagnetView)
    FloatingView.get().add()
    FloatingView.get().attach(activity)
  }

  fun setData(data: List<MainCommonVhModel>) {
    MainScope().launch {
      delay(2000)
      rvAdapter.list = data
    }
  }
}
