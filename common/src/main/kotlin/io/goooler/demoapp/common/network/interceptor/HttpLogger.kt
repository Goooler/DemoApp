package io.goooler.demoapp.common.network.interceptor

import io.goooler.demoapp.common.util.LogUtil
import okhttp3.logging.HttpLoggingInterceptor

class HttpLogger private constructor() : HttpLoggingInterceptor.Logger {

    override fun log(message: String) {
        LogUtil.d("networkLogTag", message)
    }

    companion object {
        fun newLogInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor(HttpLogger()).setLevel(HttpLoggingInterceptor.Level.BASIC)
        }
    }
}
