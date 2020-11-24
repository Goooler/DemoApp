package io.goooler.demoapp.common.base

import android.app.Application
import io.goooler.demoapp.base.core.BaseViewModel

abstract class BaseThemeViewModel(application: Application) : BaseViewModel(application), ITheme {

    override fun showLoading() {
    }

    override fun hideLoading() {
    }
}