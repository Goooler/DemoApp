package io.goooler.demoapp.main.ui

import android.app.Activity
import com.petterp.floatingx.FloatingX
import io.goooler.demoapp.main.databinding.FloatingLayoutBinding
import io.goooler.demoapp.main.model.MainCommonVhModel
import io.goooler.demoapp.main.ui.adapter.MainSrlRvAdapter

object EventFloatingWindow {
  private lateinit var rvAdapter: MainSrlRvAdapter

  fun show(activity: Activity) {
    rvAdapter = MainSrlRvAdapter(object : MainSrlRvAdapter.OnEventListener {})
    val binding = FloatingLayoutBinding.inflate(activity.layoutInflater).also {
      it.rvList.adapter = rvAdapter
    }
    FloatingX.install {
      setContext(activity)
      setLayoutView(binding.root)
      enableFx()
    }
  }

  fun setData(data: List<MainCommonVhModel>) {
    if (!::rvAdapter.isInitialized) return
    rvAdapter.list = data
  }
}
