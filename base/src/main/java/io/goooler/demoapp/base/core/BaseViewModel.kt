package io.goooler.demoapp.base.core

import android.app.Application
import androidx.annotation.AnyThread
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import io.goooler.demoapp.base.http.HttpResponse
import io.goooler.demoapp.base.util.isDebug
import io.goooler.demoapp.base.util.showToastInAnyThread

@Suppress("unused", "MemberVisibilityCanBePrivate")
abstract class BaseViewModel(application: Application) :
    AndroidViewModel(application), DefaultLifecycleObserver {

    protected fun checkStatusAndEntry(response: HttpResponse<*>) =
        response.status && response.entry != null

    protected fun checkStatusAndEntryWithToast(response: HttpResponse<*>): Boolean {
        return checkStatusAndEntry(response).also {
            if (!it) {
                showToast(response.message)
            }
        }
    }

    protected fun silentThrowable(throwable: Throwable) {
        if (isDebug) {
            toastThrowable(throwable)
        } else {
            // todo 日志上报
        }
    }

    protected fun toastThrowable(throwable: Throwable) {
        showToast(throwable.toString())
    }

    @AnyThread
    protected fun showToast(@StringRes strResId: Int) {
        showToast(getApplication<Application>().getString(strResId))
    }

    @AnyThread
    protected fun showToast(msg: String?) {
        msg?.showToastInAnyThread(getApplication())
    }
}