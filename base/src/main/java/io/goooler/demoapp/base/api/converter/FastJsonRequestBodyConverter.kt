package io.goooler.demoapp.base.api.converter

import androidx.annotation.Keep
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializeConfig
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Converter


/**
 * @author baronzhang
 * 16/2/25
 */
@Keep
internal class FastJsonRequestBodyConverter<T>(private val serializeConfig: SerializeConfig) :
    Converter<T, RequestBody> {

    override fun convert(value: T): RequestBody {
        return JSON.toJSONBytes(value, serializeConfig).toRequestBody(MEDIA_TYPE)
    }

    companion object {
        private val MEDIA_TYPE: MediaType = "application/json; charset=UTF-8".toMediaType()
    }
}