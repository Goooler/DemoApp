package io.goooler.demoapp.util

import com.alibaba.fastjson.JSONException
import com.alibaba.fastjson.JSONObject

/**
 * 对 fastJson 的简单封装，以便统一异常处理等操作
 */

object JsonUtil {

    fun <T> parse(jsonString: String, clazz: Class<T>): T? {
        var t: T? = null
        try {
            t = JSONObject.parseObject(jsonString, clazz)
        } catch (e: JSONException) {
            // do nothing
        }

        return t
    }

    fun toJson(o: Any): String {
        return JSONObject.toJSONString(o)
    }
}
