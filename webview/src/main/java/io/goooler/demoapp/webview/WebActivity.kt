package io.goooler.demoapp.webview

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import io.goooler.demoapp.base.core.BaseActivity
import io.goooler.demoapp.common.router.RouterManager
import io.goooler.demoapp.common.router.RouterPath

@Route(path = RouterPath.WEB)
class WebActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.extras?.getString(RouterManager.PARAMS)?.let {
            addFragment(android.R.id.content, WebFragment.newInstance(it))
        }
    }
}