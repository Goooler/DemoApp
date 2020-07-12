package io.goooler.demoapp.common.http

import android.content.Context
import io.goooler.demoapp.base.api.BaseRetrofitHelper
import io.goooler.demoapp.base.core.BaseApplication
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.fastjson.FastJsonConverterFactory

object RetrofitHelper : BaseRetrofitHelper() {

    override val baseUrl: String = "https://api.github.com/"

    override val context: Context = BaseApplication.context

    override val converterFactory: Converter.Factory = FastJsonConverterFactory.create()

    override val callAdapterFactory: CallAdapter.Factory = RxJava3CallAdapterFactory.create()
}