@file:Suppress("unused")

package io.goooler.demoapp.common.util

import com.squareup.moshi.Moshi
import java.lang.reflect.Type

object JsonUtil {
    internal val moshi: Moshi = Moshi.Builder().build()

    fun <T> fromJson(json: String, clazz: Class<T>): T? {
        return try {
            moshi.adapter(clazz).fromJson(json)
        } catch (e: Exception) {
            null
        }
    }

    fun <T> fromJson(json: String, typeOfT: Type): T? {
        return try {
            moshi.adapter<T>(typeOfT).fromJson(json)
        } catch (e: Exception) {
            null
        }
    }

    fun <T> toJson(o: T?, clazz: Class<T>): String? {
        return try {
            moshi.adapter(clazz).toJson(o)
        } catch (e: Exception) {
            null
        }
    }

    inline fun <reified T> toJson(o: T?): String? = toJson(o, T::class.java)
}

inline fun <reified T> String.fromJson(): T? = JsonUtil.fromJson(this, T::class.java)

inline fun <reified T> String.fromJson(typeOfT: Type): T? = JsonUtil.fromJson(this, typeOfT)

fun Any?.toJson(): String? = JsonUtil.toJson(this)