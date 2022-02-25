package io.goooler.demoapp.common.base.theme

import androidx.annotation.CallSuper
import androidx.lifecycle.LifecycleOwner
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseRxViewModel : BaseThemeViewModel() {

  private val compositeDisposable = CompositeDisposable()

  @CallSuper
  override fun onDestroy(owner: LifecycleOwner) {
    compositeDisposable.clear()
  }

  protected fun Disposable.autoDispose() {
    compositeDisposable.add(this)
  }
}
