package io.goooler.demoapp.common.http

import android.content.Context
import io.goooler.demoapp.base.core.BaseApplication
import io.goooler.demoapp.base.http.BaseRetrofitHelper
import io.goooler.demoapp.base.http.converter.fastjson.FastJsonConverterFactory
import io.goooler.demoapp.common.BuildConfig
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory

object RetrofitHelper : BaseRetrofitHelper() {

    override val baseUrl: String = BuildConfig.API_HOST

    override val context: Context = BaseApplication.context

    //todo 开了混淆之后用 fastJson 会报找不到默认构造的错
    override val converterFactory: Converter.Factory = FastJsonConverterFactory.create()

    override val callAdapterFactory: CallAdapter.Factory = RxJava3CallAdapterFactory.create()
}