package io.goooler.demoapp.base

import android.content.Context
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.File
import java.util.concurrent.TimeUnit

@Suppress("unused", "MemberVisibilityCanBePrivate")
abstract class BaseRetrofitHelper protected constructor() {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(buildOkHttpClient())
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(callAdapterFactory)
            .build()
    }

    fun <T> createApiService(service: Class<T>): T {
        return retrofit.create(service)
    }

    protected fun buildOkHttpClient(): OkHttpClient {
        val cache = Cache(File(context.cacheDir, "HttpCache"), CACHE_SIZE)
        val builder = OkHttpClient.Builder()
            .cache(cache)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor()
        return builder.build()
    }

    /**
     * 添加拦截器
     */
    protected open fun OkHttpClient.Builder.addInterceptor() = this

    /**
     * 获取 API HOST
     *
     * @return host url
     */
    protected abstract val baseUrl: String

    /**
     * 获取Context
     *
     * @return Context
     */
    protected abstract val context: Context

    protected abstract val converterFactory: Converter.Factory

    protected abstract val callAdapterFactory: CallAdapter.Factory

    companion object {
        private const val CONNECT_TIMEOUT: Long = 20
        private const val WRITE_TIMEOUT: Long = 10
        private const val READ_TIMEOUT: Long = 10

        /**
         * cache size 10 MiB
         */
        private const val CACHE_SIZE = 10 * 1024 * 1024.toLong()
    }
}