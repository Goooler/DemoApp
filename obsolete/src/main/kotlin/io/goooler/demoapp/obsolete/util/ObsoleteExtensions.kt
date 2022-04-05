package io.goooler.demoapp.obsolete.util

import io.goooler.demoapp.common.network.HttpResponse
import io.goooler.demoapp.obsolete.network.exception.ResponseException
import io.goooler.demoapp.obsolete.network.exception.toResponseException
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

@Throws(ResponseException::class)
fun <T> HttpResponse<T>.checkCodeWithException(): T? {
  if (code != 200) throw (message ?: code.toString()).toResponseException()
  return entry
}

// ---------------------Rx-------------------------------//

fun <T : Any> Single<T>.subscribeOnIoThread(): Single<T> = subscribeOn(Schedulers.io())

fun <T : Any> Single<T>.observeOnMainThread(): Single<T> = observeOn(AndroidSchedulers.mainThread())

fun <T : Any> Single<T>.subscribeAndObserve(): Single<T> =
  subscribeOnIoThread().observeOnMainThread()

fun <T : Any> Observable<T>.subscribeOnIoThread(): Observable<T> = subscribeOn(Schedulers.io())

fun <T : Any> Observable<T>.observeOnMainThread(): Observable<T> =
  observeOn(AndroidSchedulers.mainThread())

fun <T : Any> Observable<T>.subscribeAndObserve(): Observable<T> =
  subscribeOnIoThread().observeOnMainThread()
