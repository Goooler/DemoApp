@file:Suppress("unused")

package io.goooler.demoapp.base.util

import com.alibaba.fastjson.JSONObject
import com.alibaba.fastjson.TypeReference
import com.google.gson.Gson
import java.lang.reflect.Type

internal object GsonUtil {
    private val gson = Gson()

    fun <T> fromJson(json: String, classOfT: Class<T>): T? {
        return try {
            gson.fromJson<T>(json, classOfT)
        } catch (e: Exception) {
            null
        }
    }

    fun <T> fromJson(json: String, typeOfT: Type): T? {
        return try {
            gson.fromJson(json, typeOfT)
        } catch (e: Exception) {
            null
        }
    }

    fun toJson(src: Any): String = gson.toJson(src)
}

internal object FastJsonUtil {
    fun <T> fromJson(json: String, classOfT: Class<T>): T? {
        return try {
            JSONObject.parseObject(json, classOfT)
        } catch (e: Exception) {
            null
        }
    }

    fun <T> fromJson(json: String, typeOfT: Type): T? {
        return try {
            JSONObject.parseObject(json, typeOfT)
        } catch (e: Exception) {
            null
        }
    }

    fun <T> fromJson(json: String, typeRef: TypeReference<T>): T? {
        return try {
            JSONObject.parseObject(json, typeRef)
        } catch (e: Exception) {
            null
        }
    }

    fun toJson(src: Any): String {
        return JSONObject.toJSONString(src)
    }
}

object JsonUtil {
    fun <T> fromJson(json: String, clazz: Class<T>): T? =
        FastJsonUtil.fromJson(json, clazz)

    fun <T> fromJson(json: String, typeOfT: Type): T? =
        FastJsonUtil.fromJson(json, typeOfT)

    fun <T> fromJson(json: String, typeRef: TypeReference<T>): T? =
        FastJsonUtil.fromJson(json, typeRef)

    fun toJson(o: Any): String = FastJsonUtil.toJson(o)
}

inline fun <reified T> String.fromJson(): T? {
    return JsonUtil.fromJson(this, T::class.java)
}

inline fun <reified T> String.fromJson(typeOfT: Type): T? {
    return JsonUtil.fromJson(this, typeOfT)
}

inline fun <reified T> String.fromJson(typeRef: TypeReference<T>): T? {
    return JsonUtil.fromJson(this, typeRef)
}

fun Any.toJson(): String {
    return JsonUtil.toJson(this)
}