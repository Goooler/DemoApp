package io.goooler.demoapp.common.http

import android.content.Context
import io.goooler.demoapp.base.core.BaseApplication
import io.goooler.demoapp.base.http.BaseRetrofitHelper
import io.goooler.demoapp.base.http.FastJsonConverterFactory
import io.goooler.demoapp.base.util.isDebug
import io.goooler.demoapp.common.BuildConfig
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper : BaseRetrofitHelper() {

    override val baseUrl: String = BuildConfig.API_HOST

    override val context: Context = BaseApplication.context

    /**
     * 开了混淆之后用 fastJson 会报找不到默认构造的错，需声明无参构造和成员默认值
     * */
    override val converterFactory: Converter.Factory =
        if (isDebug) FastJsonConverterFactory.getInstance() else GsonConverterFactory.create()

    override val callAdapterFactory: CallAdapter.Factory = RxJava3CallAdapterFactory.create()
}