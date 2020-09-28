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
import io.goooler.demoapp.main.ui.fragment.MainPagingFragment
import io.goooler.demoapp.main.ui.fragment.MainSmartRefreshFragment

@Route(path = RouterPath.MAIN)
class MainActivity : BaseActivity() {

    private val binding by unsafeLazy { MainActivityBinding.inflate(layoutInflater) }

    private val pagerAdapter by unsafeLazy { CommonLazyFragmentPagerAdapter(supportFragmentManager) }

    private val titles = listOf(
        "首页", "smartRefresh", "paging"
    )

    private val fragments = listOf(
        MainFragment.newInstance(),
        MainSmartRefreshFragment.newInstance(),
        MainPagingFragment.newInstance()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.viewPager.offscreenPageLimit = fragments.size
        binding.viewPager.adapter = pagerAdapter

        pagerAdapter.setData(fragments, titles)
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
