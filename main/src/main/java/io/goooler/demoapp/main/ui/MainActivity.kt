package io.goooler.demoapp.main.ui

import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import io.goooler.demoapp.base.core.BaseActivity
import io.goooler.demoapp.base.util.addFragment
import io.goooler.demoapp.common.router.RouterPath

@Route(path = RouterPath.MAIN)
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.addFragment(android.R.id.content, MainFragment.newInstance())
    }

    /**
     * 不杀掉进程，直接返回桌面
     */
    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            .addCategory(Intent.CATEGORY_HOME)
        startActivity(intent)
    }
}
