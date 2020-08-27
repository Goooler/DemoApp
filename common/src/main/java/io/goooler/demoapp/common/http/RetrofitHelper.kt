package io.goooler.demoapp.common.http

import android.content.Context
import io.goooler.demoapp.base.core.BaseApplication
import io.goooler.demoapp.base.http.BaseRetrofitHelper
import io.goooler.demoapp.base.util.JsonUtil
import io.goooler.demoapp.common.BuildConfig
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitHelper : BaseRetrofitHelper() {

    override val baseUrl: String = BuildConfig.API_HOST

    override val context: Context = BaseApplication.context

    override val converterFactory: Converter.Factory = MoshiConverterFactory.create(JsonUtil.moshi)

    override val callAdapterFactory: CallAdapter.Factory = RxJava3CallAdapterFactory.create()
}