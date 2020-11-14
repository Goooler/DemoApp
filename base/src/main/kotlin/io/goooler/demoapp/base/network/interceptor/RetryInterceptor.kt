package io.goooler.demoapp.base.network.interceptor

import io.goooler.demoapp.base.network.BaseInterceptor
import okhttp3.Interceptor
import okhttp3.Response

class RetryInterceptor private constructor() : BaseInterceptor {

    @Synchronized
    override fun intercept(chain: Interceptor.Chain): Response {
        var retryCount = 0
        val request = chain.request()
        var response = chain.proceed(request)
        while (!response.isSuccessful && retryCount < MAX_RETRY_COUNT - 1) {
            retryCount++
            response.close()
            response = chain.proceed(request)
        }
        return response
    }

    companion object {
        private const val MAX_RETRY_COUNT = 3

        fun create() = RetryInterceptor()
    }
}
