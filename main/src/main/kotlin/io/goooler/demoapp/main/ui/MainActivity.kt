package io.goooler.demoapp.main.ui

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.alibaba.android.arouter.facade.annotation.Route
import dagger.hilt.android.AndroidEntryPoint
import io.goooler.demoapp.adapter.vp.CommonFragmentStatePagerAdapter
import io.goooler.demoapp.base.util.unsafeLazy
import io.goooler.demoapp.common.base.binding.BaseBindingActivity
import io.goooler.demoapp.common.router.RouterPath
import io.goooler.demoapp.common.util.showToast
import io.goooler.demoapp.main.databinding.MainActivityBinding
import io.goooler.demoapp.main.ui.fragment.MainHomeFragment
import io.goooler.demoapp.main.ui.fragment.MainPagingFragment
import io.goooler.demoapp.main.ui.fragment.MainSrlFragment

@AndroidEntryPoint
@Route(path = RouterPath.MAIN)
class MainActivity : BaseBindingActivity<MainActivityBinding>() {

  private val pagerAdapter by unsafeLazy {
    CommonFragmentStatePagerAdapter(supportFragmentManager)
  }

  private val titles = listOf("home", "smartRefresh", "paging")

  private val requestPermissionsLauncher = registerForActivityResult(
    ActivityResultContracts.RequestMultiplePermissions()
  ) {
    it.entries.forEach { entry ->
      if (!entry.value) "${entry.key} has not been granted".showToast()
    }
  }

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

    requestPermissionsLauncher.launch(
      arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA,
        Manifest.permission.ACCESS_COARSE_LOCATION
      )
    )
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
}
