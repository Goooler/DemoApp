package io.goooler.demoapp.common.network.interceptor

import io.goooler.demoapp.common.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

object HeaderInterceptor : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    val builder = chain.request().newBuilder()
      .header("versionName", BuildConfig.VERSION_NAME)
      .header("versionCode", BuildConfig.VERSION_CODE.toString())
      .header("debug", BuildConfig.DEBUG.toString())
    return chain.proceed(builder.build())
  }
}
