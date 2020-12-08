package io.goooler.demoapp.base.network

import android.content.Context
import io.goooler.demoapp.base.network.interceptor.RetryInterceptor
import io.goooler.demoapp.base.network.interceptor.StatusInterceptor
import java.io.File
import java.util.concurrent.TimeUnit
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

@Suppress("unused")
abstract class BaseRetrofitHelper {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(buildOkHttpClient())
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory()
            .build()
    }

    fun <T> create(service: Class<T>): T = retrofit.create(service)

    inline fun <reified T> create(): T = create(T::class.java)

    protected open fun OkHttpClient.Builder.addInterceptor(): OkHttpClient.Builder = this

    protected open fun Retrofit.Builder.addCallAdapterFactory(): Retrofit.Builder = this

    protected abstract val baseUrl: String

    protected abstract val context: Context

    protected abstract val converterFactory: Converter.Factory

    protected abstract val statusListener: StatusInterceptor.StatusListener

    protected open fun buildOkHttpClient(): OkHttpClient {
        val cache = Cache(File(context.cacheDir, "HttpCache"), 10L * 1024 * 1024)
        return OkHttpClient.Builder()
            .cache(cache)
            .connectTimeout(20L, TimeUnit.SECONDS)
            .addInterceptor(StatusInterceptor.create(statusListener))
            .addInterceptor(RetryInterceptor.create())
            .addInterceptor()
            .build()
    }
}
