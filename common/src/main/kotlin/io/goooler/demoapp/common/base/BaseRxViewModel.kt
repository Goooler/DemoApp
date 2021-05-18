package io.goooler.demoapp.common.base

import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseRxViewModel : BaseThemeViewModel() {

  private val compositeDisposable = CompositeDisposable()

  @CallSuper
  @MainThread
  override fun onDestroy(owner: LifecycleOwner) {
    compositeDisposable.clear()
  }

  fun addDisposable(disposable: Disposable) {
    compositeDisposable.add(disposable)
  }

  fun Disposable.autoDispose() {
    addDisposable(this)
  }
}
