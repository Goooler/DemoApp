package io.goooler.demoapp.test.convertfactory

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializeConfig
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

object FastJsonConverterFactory : Converter.Factory() {

    fun getInstance(): FastJsonConverterFactory = this

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<Any, RequestBody> =
        FastJsonRequestBodyConverter(SerializeConfig.getGlobalInstance())

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, Any> = FastJsonResponseBodyConvert(type)
}

internal class FastJsonRequestBodyConverter<T>(private val serializeConfig: SerializeConfig) :
    Converter<T, RequestBody> {

    override fun convert(value: T): RequestBody = JSON.toJSONBytes(value, serializeConfig)
        .toRequestBody("application/json; charset=UTF-8".toMediaType())
}

internal class FastJsonResponseBodyConvert<T>(private val type: Type) :
    Converter<ResponseBody, T> {

    override fun convert(value: ResponseBody): T = JSON.parseObject(value.string(), type)
}