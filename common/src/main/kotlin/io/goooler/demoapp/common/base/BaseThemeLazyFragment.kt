package io.goooler.demoapp.common.base

import io.goooler.demoapp.base.core.BaseLazyFragment

abstract class BaseThemeLazyFragment : BaseLazyFragment(), ITheme {

    override fun showLoading() {
    }

    override fun hideLoading() {
    }
}