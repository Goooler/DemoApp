package io.goooler.demoapp.base.network

import android.content.Context
import okhttp3.Cache
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.File
import java.util.concurrent.TimeUnit

@Suppress("unused")
abstract class BaseRetrofitHelper {

  private val retrofit by lazy {
    Retrofit.Builder()
      .baseUrl(baseUrl)
      .client(buildOkHttpClient())
      .addConverterFactory(converterFactory)
      .addCallAdapterFactories()
      .build()
  }

  private val statusInterceptor = Interceptor { chain ->
    chain.proceed(chain.request()).also {
      when (it.code) {
        401, 407 -> statusListener.onAuthFailed()
        403 -> statusListener.onForbidden()
        404 -> statusListener.onNotFound()
      }
    }
  }

  fun <T> create(service: Class<T>): T = retrofit.create(service)

  inline fun <reified T> create(): T = create(T::class.java)

  protected abstract fun OkHttpClient.Builder.addInterceptors(): OkHttpClient.Builder

  protected abstract val baseUrl: String

  protected abstract val context: Context

  protected abstract val converterFactory: Converter.Factory

  protected abstract val statusListener: StatusListener

  protected open fun Retrofit.Builder.addCallAdapterFactories(): Retrofit.Builder = this

  protected open fun buildOkHttpClient(): OkHttpClient {
    val cache = Cache(File(context.cacheDir, "HttpCache"), 10L * 1024 * 1024)
    val dispatcher = Dispatcher().apply {
      maxRequestsPerHost = maxRequests / 2
    }
    return OkHttpClient.Builder()
      .cache(cache)
      .connectTimeout(20L, TimeUnit.SECONDS)
      .dispatcher(dispatcher)
      .addInterceptor(statusInterceptor)
      .addInterceptors()
      .build()
  }

  fun interface StatusListener {
    fun onAuthFailed()
    fun onForbidden() {}
    fun onNotFound() {}
  }
}
