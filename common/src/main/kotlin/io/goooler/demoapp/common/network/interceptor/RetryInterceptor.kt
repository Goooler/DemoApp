package io.goooler.demoapp.common.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

object RetryInterceptor : Interceptor {

  private const val MAX_RETRY_COUNT = 3

  @Synchronized
  override fun intercept(chain: Interceptor.Chain): Response {
    var retryCount = 0
    val request = chain.request()
    var response = chain.proceed(request)
    while (response.isSuccessful.not() && retryCount < MAX_RETRY_COUNT - 1) {
      retryCount++
      response.close()
      response = chain.proceed(request)
    }
    return response
  }
}
