package io.goooler.demoapp.main.ui

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import io.goooler.demoapp.base.core.BaseActivity
import io.goooler.demoapp.base.model.RouterPath

@Route(path = RouterPath.MAIN)
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addFragment(android.R.id.content, MainFragment.newInstance())
    }
}
