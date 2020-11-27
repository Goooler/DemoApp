package io.goooler.demoapp.common.base

import android.content.res.Resources
import android.os.Bundle
import com.blankj.utilcode.util.AdaptScreenUtils
import com.blankj.utilcode.util.BarUtils
import io.goooler.demoapp.base.core.BaseActivity

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseThemeActivity : BaseActivity(), ITheme {

    val originalResources: Resources get() = super.getResources()

    override fun getResources(): Resources = AdaptScreenUtils.adaptWidth(originalResources, 360)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BarUtils.transparentStatusBar(this)
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }
}