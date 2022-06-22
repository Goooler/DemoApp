package io.goooler.demoapp.main.ui

import android.Manifest
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import io.goooler.demoapp.adapter.vp.CommonFragmentStatePagerAdapter
import io.goooler.demoapp.base.util.PermissionHelper
import io.goooler.demoapp.base.util.unsafeLazy
import io.goooler.demoapp.common.base.binding.BaseBindingActivity
import io.goooler.demoapp.common.util.log
import io.goooler.demoapp.main.databinding.MainActivityBinding
import io.goooler.demoapp.main.ui.fragment.MainHomeFragment
import io.goooler.demoapp.main.ui.fragment.MainPagingFragment
import io.goooler.demoapp.main.ui.fragment.MainSrlFragment

@AndroidEntryPoint
class MainActivity : BaseBindingActivity<MainActivityBinding>() {

  private val pagerAdapter by unsafeLazy { CommonFragmentStatePagerAdapter(supportFragmentManager) }

  private val titles = listOf("home", "smartRefresh", "paging")

  private val fragments = listOf(
    MainHomeFragment(),
    MainSrlFragment(),
    MainPagingFragment()
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding.run {
      viewPager.offscreenPageLimit = fragments.size
      viewPager.adapter = pagerAdapter
      pagerAdapter.setData(fragments, titles)
      // setViewPager 必须在 pagerAdapter 设置数据之后
      tabLayout.setViewPager(viewPager)
    }

    PermissionHelper.with(this)
      .permissions(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA,
        Manifest.permission.ACCESS_COARSE_LOCATION
      )
      .onGranted { permissions ->
        permissions.forEach { it.log() }
      }
      .onDenied { permissions ->
        permissions.forEach { it.log() }
      }
      .request()
  }

  override fun onBackPressed() {
    moveTaskToBack(false)
  }
}
