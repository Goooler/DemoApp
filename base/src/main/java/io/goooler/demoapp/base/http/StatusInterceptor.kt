package io.goooler.demoapp.base.http

import okhttp3.Interceptor
import okhttp3.Response

class StatusInterceptor(private val listener: StatusListener) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        when (response.code) {
            401 -> listener.onAuthFailed()
        }
        return response
    }

    fun interface StatusListener {
        fun onAuthFailed()
    }
}