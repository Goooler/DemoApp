package io.goooler.demoapp.common.base

import io.goooler.demoapp.base.core.BaseFragment

abstract class BaseThemeFragment : BaseFragment(), ITheme {

    override fun showLoading() {
    }

    override fun hideLoading() {
    }
}
