package io.goooler.demoapp.common.network.interceptor

import io.goooler.demoapp.common.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

object HeaderInterceptor : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request().newBuilder()
      .header("flavor", BuildConfig.FLAVOR)
      .header("versionName", BuildConfig.VERSION_NAME)
      .header("versionCode", BuildConfig.VERSION_CODE.toString())
      .header("debug", BuildConfig.DEBUG.toString())
      .build()
    return chain.proceed(request)
  }
}
