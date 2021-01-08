package io.goooler.demoapp.map.ui

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import io.goooler.demoapp.base.util.addFragment
import io.goooler.demoapp.common.base.BaseThemeActivity
import io.goooler.demoapp.common.router.RouterPath
import io.goooler.demoapp.map.R
import io.goooler.demoapp.map.databinding.MapActivityBinding
import io.goooler.demoapp.map.ui.fragment.AmapFragment

@Route(path = RouterPath.MAP)
class MapActivity : BaseThemeActivity<MapActivityBinding>(R.layout.map_activity) {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    supportFragmentManager.addFragment(R.id.fragment_container, AmapFragment.newInstance())
  }
}
