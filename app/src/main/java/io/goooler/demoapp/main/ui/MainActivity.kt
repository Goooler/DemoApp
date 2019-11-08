package io.goooler.demoapp.main.ui

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import io.goooler.demoapp.base.BaseActivity
import io.goooler.demoapp.base.BaseApplication
import io.goooler.demoapp.model.RouterPath

@Route(path = RouterPath.MAIN)
class MainActivity : BaseActivity() {

    private val mainFragment by lazy {
        MainFragment.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addFragment(android.R.id.content, mainFragment)
    }

    override fun onDestroy() {
        BaseApplication.destroyGlobalObject()
        super.onDestroy()
    }
}
