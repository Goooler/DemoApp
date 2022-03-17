package io.goooler.demoapp.obsolete.network.exception

import io.goooler.demoapp.common.network.BaseResponse
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.observers.DisposableSingleObserver

sealed interface CustomObserver<T : Any> {

  fun onCustomSuccess(t: T)

  fun onCustomError(e: ResponseException)
}

abstract class CustomObservableObserver<T : BaseResponse> :
  DisposableObserver<T>(),
  CustomObserver<T> {

  override fun onNext(t: T) {
    if (t.code == 200)
      onCustomSuccess(t)
    else
      onCustomError((t.message ?: t.code.toString()).toResponseException())
  }

  override fun onError(e: Throwable) {
    onCustomError(e.toResponseException())
  }

  override fun onComplete() {}
}

abstract class CustomSingleObserver<T : BaseResponse> :
  DisposableSingleObserver<T>(),
  CustomObserver<T> {

  override fun onSuccess(t: T) {
    if (t.code == 200)
      onCustomSuccess(t)
    else
      onCustomError((t.message ?: t.code.toString()).toResponseException())
  }

  override fun onError(e: Throwable) {
    onCustomError(e.toResponseException())
  }
}
