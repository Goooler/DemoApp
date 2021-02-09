package io.goooler.demoapp.common.network

import android.annotation.SuppressLint
import android.content.Context
import io.goooler.demoapp.base.core.BaseApplication
import io.goooler.demoapp.base.network.BaseRetrofitHelper
import io.goooler.demoapp.base.network.interceptor.StatusInterceptor
import io.goooler.demoapp.common.BuildConfig
import io.goooler.demoapp.common.network.interceptor.CookieInterceptor
import io.goooler.demoapp.common.network.interceptor.HttpLogger
import io.goooler.demoapp.common.router.RouterManager
import io.goooler.demoapp.common.util.AppUserInfoManager
import io.goooler.demoapp.common.util.JsonUtil
import okhttp3.OkHttpClient
import okhttp3.brotli.BrotliInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@SuppressLint("StaticFieldLeak")
object RetrofitHelper : BaseRetrofitHelper() {

  override val baseUrl: String = BuildConfig.API_HOST

  override val context: Context = BaseApplication.app

  override val converterFactory: Converter.Factory = MoshiConverterFactory.create(JsonUtil.moshi)

  override val statusListener: StatusInterceptor.StatusListener = StatusInterceptor.StatusListener {
    AppUserInfoManager.resetUserInfo()
    RouterManager.goLogin(true)
  }

  override fun Retrofit.Builder.addCallAdapterFactory(): Retrofit.Builder {
    addCallAdapterFactory(RxJava3CallAdapterFactory.create())
    return this
  }

  override fun OkHttpClient.Builder.addInterceptor(): OkHttpClient.Builder {
    addInterceptor(CookieInterceptor)
    addInterceptor(BrotliInterceptor)
    addNetworkInterceptor(HttpLogger.newLogInterceptor())
    return this
  }
}
