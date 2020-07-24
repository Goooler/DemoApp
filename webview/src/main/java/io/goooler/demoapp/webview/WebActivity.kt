package io.goooler.demoapp.webview

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import io.goooler.demoapp.base.core.BaseActivity
import io.goooler.demoapp.common.router.RouterManager
import io.goooler.demoapp.common.router.RouterPath

@Route(path = RouterPath.WEB)
class WebActivity : BaseActivity() {

    private lateinit var webFragment: WebFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.extras?.getString(RouterManager.PARAMS)?.let {
            webFragment = WebFragment.newInstance(it)
            addFragment(android.R.id.content, webFragment)
        }
    }

    override fun onBackPressed() {
        webFragment.goBack()
    }
}