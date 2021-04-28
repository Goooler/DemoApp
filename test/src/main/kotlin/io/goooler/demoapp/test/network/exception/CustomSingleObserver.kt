package io.goooler.demoapp.test.network.exception

import io.goooler.demoapp.common.network.BaseResponse
import io.reactivex.rxjava3.observers.DisposableSingleObserver

abstract class CustomSingleObserver<T : BaseResponse> : DisposableSingleObserver<T>(),
  CustomObserver<T> {

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

interface CustomObserver<T> {

  fun onCustomSuccess(t: T)

  fun onCustomError(e: ResponseException)
}
