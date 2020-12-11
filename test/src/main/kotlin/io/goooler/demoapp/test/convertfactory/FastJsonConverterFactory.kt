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

  val instance: FastJsonConverterFactory = this

  override fun requestBodyConverter(
    type: Type,
    parameterAnnotations: Array<Annotation>,
    methodAnnotations: Array<Annotation>,
    retrofit: Retrofit
  ): Converter<Any, RequestBody> = Converter {
    JSON.toJSONBytes(it, SerializeConfig.getGlobalInstance())
      .toRequestBody("application/json; charset=UTF-8".toMediaType())
  }

  override fun responseBodyConverter(
    type: Type,
    annotations: Array<Annotation>,
    retrofit: Retrofit
  ): Converter<ResponseBody, Any> = Converter {
    JSON.parseObject(it.string(), type)
  }
}
