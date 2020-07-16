package io.goooler.demoapp.base.http.converter.fastjson

import androidx.annotation.Keep
import com.alibaba.fastjson.serializer.SerializeConfig
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * @author baronzhang
 * 16/2/25
 */
@Keep
class FastJsonConverterFactory private constructor(private val serializeConfig: SerializeConfig) :
    Converter.Factory() {
    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody> {
        return FastJsonRequestBodyConverter<Any>(serializeConfig)
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        return FastJsonResponseBodyConvert<Any>(type)
    }

    companion object {
        fun create(serializeConfig: SerializeConfig = SerializeConfig.getGlobalInstance()): FastJsonConverterFactory {
            return FastJsonConverterFactory(serializeConfig)
        }
    }
}