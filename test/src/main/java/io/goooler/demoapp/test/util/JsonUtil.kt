@file:Suppress("unused")

package io.goooler.demoapp.test.util

import com.alibaba.fastjson.JSONObject
import com.alibaba.fastjson.TypeReference
import com.google.gson.Gson
import java.lang.reflect.Type

internal object GsonUtil {
    private val gson = Gson()

    fun <T> fromJson(json: String, classOfT: Class<T>): T? {
        return try {
            gson.fromJson(json, classOfT)
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