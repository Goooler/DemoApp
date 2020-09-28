package io.goooler.demoapp.main.ui

import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import io.goooler.demoapp.adapter.vp.CommonLazyFragmentPagerAdapter
import io.goooler.demoapp.base.core.BaseActivity
import io.goooler.demoapp.base.util.unsafeLazy
import io.goooler.demoapp.common.router.RouterPath
import io.goooler.demoapp.main.databinding.MainActivityBinding
import io.goooler.demoapp.main.ui.fragment.MainFragment
import io.goooler.demoapp.main.ui.fragment.MainListFragment

@Route(path = RouterPath.MAIN)
class MainActivity : BaseActivity() {

    private val binding by unsafeLazy { MainActivityBinding.inflate(layoutInflater) }

    private val titles = listOf(
        "首页", "列表"
    )

    private val fragments = listOf(
        MainFragment.newInstance(), MainListFragment.newInstance()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val pagerAdapter = CommonLazyFragmentPagerAdapter(supportFragmentManager)
        pagerAdapter.setData(fragments, titles)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.viewPager.offscreenPageLimit = 10
        binding.viewPager.adapter = pagerAdapter
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
