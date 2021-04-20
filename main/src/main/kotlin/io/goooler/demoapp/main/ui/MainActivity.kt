package io.goooler.demoapp.main.ui

import android.Manifest
import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayoutMediator
import com.permissionx.guolindev.PermissionX
import dagger.hilt.android.AndroidEntryPoint
import io.goooler.demoapp.adapter.vp.CommonFragmentStateAdapter
import io.goooler.demoapp.base.util.unsafeLazy
import io.goooler.demoapp.common.base.BaseThemeActivity
import io.goooler.demoapp.common.router.RouterPath
import io.goooler.demoapp.main.R
import io.goooler.demoapp.main.databinding.MainActivityBinding
import io.goooler.demoapp.main.ui.fragment.MainHomeFragment
import io.goooler.demoapp.main.ui.fragment.MainPagingFragment
import io.goooler.demoapp.main.ui.fragment.MainSrlFragment

@AndroidEntryPoint
@Route(path = RouterPath.MAIN)
class MainActivity : BaseThemeActivity<MainActivityBinding>(R.layout.main_activity) {

  private val pagerAdapter by unsafeLazy {
    CommonFragmentStateAdapter(supportFragmentManager, lifecycle)
  }

  private val titles = listOf("home", "smartRefresh", "paging")

  private val fragments = listOf(
    MainHomeFragment.newInstance(),
    MainSrlFragment.newInstance(),
    MainPagingFragment.newInstance()
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding.run {
      viewPager.offscreenPageLimit = fragments.size
      viewPager.adapter = pagerAdapter
      TabLayoutMediator(tabLayout, viewPager) { tab, position ->
        tab.text = titles[position]
      }.attach()
    }
    pagerAdapter.setData(fragments)

    requestPermissions()
  }

  /**
   * 不杀掉进程，直接返回桌面
   */
  override fun onBackPressed() {
    if (supportFragmentManager.backStackEntryCount == 0) {
      val intent = Intent(Intent.ACTION_MAIN)
        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        .addCategory(Intent.CATEGORY_HOME)
      startActivity(intent)
    } else {
      super.onBackPressed()
    }
  }

  private fun requestPermissions() {
    PermissionX.init(this)
      .permissions(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA,
        Manifest.permission.ACCESS_COARSE_LOCATION
      ).onExplainRequestReason { scope, deniedList ->
        scope.showRequestReasonDialog(
          deniedList,
          "Core fundamental are based on these permissions",
          "OK",
          "Cancel"
        )
      }
      .onForwardToSettings { scope, deniedList ->
        scope.showForwardToSettingsDialog(
          deniedList,
          "You need to allow necessary permissions in Settings manually",
          "OK",
          "Cancel"
        )
      }
      .request { _, _, _ -> }
  }
}
