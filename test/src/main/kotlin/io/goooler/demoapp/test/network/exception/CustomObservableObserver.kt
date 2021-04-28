package io.goooler.demoapp.test.network.exception

import io.goooler.demoapp.common.network.BaseResponse
import io.reactivex.rxjava3.observers.DisposableObserver

abstract class CustomObservableObserver<T : BaseResponse> :
  DisposableObserver<T>(),
  CustomObserver<T> {

  override fun onNext(t: T) {
    if (t.status && t.code == 200)
      onCustomSuccess(t)
    else
      onCustomError((t.message ?: t.code.toString()).toResponseException())
  }

  override fun onError(e: Throwable?) {
    onCustomError(e.toResponseException())
  }

  override fun onComplete() {}
}
