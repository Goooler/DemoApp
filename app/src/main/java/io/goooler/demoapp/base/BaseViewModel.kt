package io.goooler.demoapp.base

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import io.goooler.demoapp.BuildConfig
import io.goooler.demoapp.api.HttpResponse
import io.goooler.demoapp.util.LogUtil
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


open class BaseViewModel(application: Application) : AndroidViewModel(application), DefaultLifecycleObserver {

    private val compositeDisposable = CompositeDisposable()
    val toast = MutableStringLiveData()

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        compositeDisposable.clear()
    }

    protected fun checkStatusAndEntry(response: HttpResponse<*>): Boolean {
        return response.status && response.entry != null
    }

    protected fun checkStatusAndEntryWithToast(response: HttpResponse<*>): Boolean {
        return checkStatusAndEntry(response).also {
            if (!it) {
                showToast(response.message)
            }
        }
    }

    protected fun silentThrowable(throwable: Throwable) {
        if (BuildConfig.DEBUG) {
            showToast(throwable.toString())
        } else {
            LogUtil.d(throwable)
        }
    }

    protected fun showToast(@StringRes strResId: Int) {
        toast.postValue(getString(strResId))
    }

    protected fun showToast(msg: String?) {
        msg?.let {
            toast.postValue(it)
        }
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
        return String.format(getApplication<Application>().getString(resId), *args)
    }
}