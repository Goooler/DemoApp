package io.goooler.demoapp.base.http

import android.content.Context
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.File
import java.util.concurrent.TimeUnit

@Suppress("unused", "MemberVisibilityCanBePrivate")
abstract class BaseRetrofitHelper {

    private val retrofit by lazy {
        val builder = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(buildOkHttpClient())
            .addConverterFactory(converterFactory)
        callAdapterFactory?.let {
            builder.addCallAdapterFactory(it)
        }
        builder.build()
    }

    fun <T> create(service: Class<T>): T = retrofit.create(service)

    inline fun <reified T> create(): T = create(T::class.java)

    protected fun buildOkHttpClient(): OkHttpClient {
        val cache = Cache(
            File(context.cacheDir, "HttpCache"),
            CACHE_SIZE
        )
        val builder = OkHttpClient.Builder()
            .cache(cache)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor()
        return builder.build()
    }

    protected open fun OkHttpClient.Builder.addInterceptor(): OkHttpClient.Builder = this

    protected abstract val baseUrl: String

    protected abstract val context: Context

    protected abstract val converterFactory: Converter.Factory

    protected abstract val callAdapterFactory: CallAdapter.Factory?

    companion object {
        private const val CONNECT_TIMEOUT: Long = 20
        private const val WRITE_TIMEOUT: Long = 10
        private const val READ_TIMEOUT: Long = 10
        private const val CACHE_SIZE = 10 * 1024 * 1024.toLong()
    }
}