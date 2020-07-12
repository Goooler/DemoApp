package io.goooler.demoapp.base.api.converter

import androidx.annotation.Keep
import com.alibaba.fastjson.JSON
import okhttp3.ResponseBody
import retrofit2.Converter
import java.lang.reflect.Type

/**
 * @author baronzhang
 * 16/2/25
 */
@Keep
internal class FastJsonResponseBodyConvert<T>(private val type: Type) : Converter<ResponseBody, T> {
    override fun convert(value: ResponseBody): T {
        return JSON.parseObject(value.string(), type)
    }
}