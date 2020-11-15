package io.goooler.demoapp.common.base

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import io.goooler.demoapp.base.core.BaseViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseRxViewModel(application: Application) : BaseViewModel(application) {

    private val compositeDisposable = CompositeDisposable()

    override fun onDestroy(owner: LifecycleOwner) {
        compositeDisposable.clear()
    }

    protected fun Disposable.autoDispose() {
        compositeDisposable.add(this)
    }
}