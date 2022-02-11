package io.goooler.demoapp.common.network

import android.annotation.SuppressLint
import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import io.goooler.demoapp.base.network.BaseRetrofitHelper
import io.goooler.demoapp.base.network.BaseRetrofitHelper.StatusListener
import io.goooler.demoapp.common.BuildConfig
import io.goooler.demoapp.common.CommonApplication
import io.goooler.demoapp.common.network.interceptor.HeaderInterceptor
import io.goooler.demoapp.common.router.RouterManager
import io.goooler.demoapp.common.util.AppUserInfoManager
import io.goooler.demoapp.common.util.JsonUtil
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@SuppressLint("StaticFieldLeak")
object RetrofitHelper : BaseRetrofitHelper() {

  override val baseUrl: String = BuildConfig.API_HOST

  override val context: Context = CommonApplication.app

  override val converterFactory: Converter.Factory = MoshiConverterFactory.create(JsonUtil.moshi)

  override val statusListener: StatusListener = StatusListener {
    AppUserInfoManager.logout()
    RouterManager.goLogin(CommonApplication.app, true)
  }

  override fun Retrofit.Builder.addCallAdapterFactories(): Retrofit.Builder {
    addCallAdapterFactory(RxJava3CallAdapterFactory.create())
    return this
  }

  override fun OkHttpClient.Builder.addInterceptors(): OkHttpClient.Builder {
    addInterceptor(HeaderInterceptor)
    addNetworkInterceptor(ChuckerInterceptor.Builder(context).build())
    return this
  }
}
