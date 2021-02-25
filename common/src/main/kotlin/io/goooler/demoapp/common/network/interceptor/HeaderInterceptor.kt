package io.goooler.demoapp.common.network.interceptor

import io.goooler.demoapp.common.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

object HeaderInterceptor : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    val builder = chain.request().newBuilder()
      .addHeader("versionName", BuildConfig.VERSION_NAME)
      .addHeader("versionCode", BuildConfig.VERSION_CODE.toString())
      .addHeader("debug", BuildConfig.DEBUG.toString())
    return chain.proceed(builder.build())
  }
}
