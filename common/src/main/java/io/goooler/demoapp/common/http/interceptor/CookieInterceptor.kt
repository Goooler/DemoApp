package io.goooler.demoapp.common.http.interceptor

import io.goooler.demoapp.base.network.BaseInterceptor
import io.goooler.demoapp.common.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class CookieInterceptor private constructor() : BaseInterceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
            .addHeader("versionName", BuildConfig.VERSION_NAME)
            .addHeader("versionCode", BuildConfig.VERSION_CODE.toString())
            .addHeader("debug", BuildConfig.DEBUG.toString())
        return chain.proceed(builder.build())
    }

    companion object {
        fun create() = CookieInterceptor()
    }
}