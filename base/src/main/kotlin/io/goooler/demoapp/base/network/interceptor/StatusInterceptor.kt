package io.goooler.demoapp.base.network.interceptor

import io.goooler.demoapp.base.network.BaseInterceptor
import okhttp3.Interceptor
import okhttp3.Response

class StatusInterceptor private constructor(private val listener: StatusListener) :
  BaseInterceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    return chain.proceed(chain.request()).also {
      when (it.code) {
        401, 407 -> listener.onAuthFailed()
        403 -> listener.onForbidden()
        404 -> listener.onNotFound()
      }
    }
  }

  fun interface StatusListener {
    fun onAuthFailed()
    fun onForbidden() {}
    fun onNotFound() {}
  }

  companion object {
    fun create(listener: StatusListener) = StatusInterceptor(listener)
  }
}
