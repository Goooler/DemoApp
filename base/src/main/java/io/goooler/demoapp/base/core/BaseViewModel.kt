package io.goooler.demoapp.base.core

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.annotation.AnyThread
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import io.goooler.demoapp.base.http.HttpResponse
import io.goooler.demoapp.base.util.isDebug
import io.goooler.demoapp.base.util.showToastInAnyThread
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

@Suppress("unused", "MemberVisibilityCanBePrivate")
abstract class BaseViewModel(application: Application) :
    AndroidViewModel(application), DefaultLifecycleObserver {

    private val compositeDisposable = CompositeDisposable()

    override fun onDestroy(owner: LifecycleOwner) {
        compositeDisposable.clear()
    }

    protected fun Disposable.add() {
        compositeDisposable.add(this)
    }

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
        showToast(getString(strResId))
    }

    @AnyThread
    protected fun showToast(msg: String?) {
        msg?.showToastInAnyThread(getApplication())
    }

    protected fun getColor(@ColorRes id: Int): Int {
        return ContextCompat.getColor(getApplication(), id)
    }

    protected fun getDrawable(@DrawableRes id: Int): Drawable? {
        return ContextCompat.getDrawable(getApplication(), id)
    }

    protected fun getString(@StringRes id: Int): String {
        return getApplication<Application>().getString(id)
    }

    protected fun formatResString(@StringRes resId: Int, vararg args: Any): String {
        return String.format(getApplication<Application>().getString(resId), args)
    }
}