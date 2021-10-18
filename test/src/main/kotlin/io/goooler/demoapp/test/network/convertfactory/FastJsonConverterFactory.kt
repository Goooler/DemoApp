package io.goooler.demoapp.test.network.convertfactory

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializeConfig
import java.lang.reflect.Type
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit

object FastJsonConverterFactory : Converter.Factory() {

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
