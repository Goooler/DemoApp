package io.goooler.demoapp.test.network.exception

import io.goooler.demoapp.common.network.BaseResponse
import io.reactivex.rxjava3.observers.DisposableSingleObserver

abstract class CustomObserver<T : BaseResponse> : DisposableSingleObserver<T>() {

  abstract fun onCustomSuccess(t: T)

  abstract fun onCustomError(e: ResponseException)

  override fun onSuccess(t: T) {
    if (t.status && t.code == 200)
      onCustomSuccess(t)
    else
      onCustomError((t.message ?: t.code.toString()).toResponseException())
  }

  override fun onError(e: Throwable) {
    onCustomError(e.toResponseException())
  }
}
