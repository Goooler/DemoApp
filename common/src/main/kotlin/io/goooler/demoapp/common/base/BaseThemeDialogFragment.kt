package io.goooler.demoapp.common.base

import io.goooler.demoapp.base.core.BaseDialogFragment

abstract class BaseThemeDialogFragment : BaseDialogFragment(), ITheme {

    override fun showLoading() {
    }

    override fun hideLoading() {
    }
}
