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
            e.printStackTrace()
            null
        }
    }

    fun <T> fromJson(json: String, typeOfT: Type): T? {
        return try {
            gson.fromJson(json, typeOfT)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun toJson(src: Any): String? {
        return try {
            gson.toJson(src)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

internal object FastJsonUtil {
    fun <T> fromJson(json: String, classOfT: Class<T>): T? {
        return try {
            JSONObject.parseObject(json, classOfT)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun <T> fromJson(json: String, typeOfT: Type): T? {
        return try {
            JSONObject.parseObject(json, typeOfT)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun <T> fromJson(json: String, typeRef: TypeReference<T>): T? {
        return try {
            JSONObject.parseObject(json, typeRef)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun toJson(src: Any): String? {
        return try {
            JSONObject.toJSONString(src)
        } catch (e: Exception) {
            null
        }
    }
}
