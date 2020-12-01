package io.goooler.demoapp.common.base

import android.app.Application
import io.goooler.demoapp.base.core.BaseViewModel
import io.goooler.demoapp.common.network.HttpResponse
import io.goooler.demoapp.common.util.showToast

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseThemeViewModel(application: Application) : BaseViewModel(application), ITheme {

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    protected fun checkStatusAndEntry(response: HttpResponse<*>) =
        response.status && response.entry != null

    protected fun checkStatusAndEntryWithToast(response: HttpResponse<*>): Boolean {
        return checkStatusAndEntry(response).also {
            if (!it) {
                response.message?.showToast()
            }
        }
    }

    protected fun toastThrowable(throwable: Throwable) {
        throwable.toString().showToast()
    }
}