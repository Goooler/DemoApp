package io.goooler.demoapp.obsolete.network.interceptor

import io.goooler.demoapp.common.util.LogUtil
import okhttp3.logging.HttpLoggingInterceptor

class HttpLogInterceptor private constructor() : HttpLoggingInterceptor.Logger {

  override fun log(message: String) {
    LogUtil.d("networkLogTag", message)
  }

  companion object {
    operator fun invoke(): HttpLoggingInterceptor =
      HttpLoggingInterceptor(HttpLogInterceptor()).setLevel(HttpLoggingInterceptor.Level.BASIC)
  }
}
