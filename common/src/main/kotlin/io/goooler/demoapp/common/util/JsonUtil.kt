@file:Suppress("unused")

package io.goooler.demoapp.common.util

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

object JsonUtil {
  @PublishedApi
  internal val moshi: Moshi = Moshi.Builder().build()

  inline fun <reified T> fromJson(json: String): T? = try {
    moshi.adapter(T::class.java).fromJson(json)
  } catch (e: Exception) {
    e.printStackTrace()
    null
  }

  inline fun <reified T> fromJson(
    json: String,
    rawType: Class<*>,
    vararg typeArguments: Class<*>
  ): T? = try {
    moshi.adapter<T>(Types.newParameterizedType(rawType, *typeArguments)).fromJson(json)
  } catch (e: Exception) {
    e.printStackTrace()
    null
  }

  inline fun <reified T> toJson(o: T?): String? = try {
    moshi.adapter(T::class.java).toJson(o)
  } catch (e: Exception) {
    e.printStackTrace()
    null
  }
}

inline fun <reified T> String.fromJson(): T? = JsonUtil.fromJson(this, T::class.java)

inline fun <reified T> String.fromJson(rawType: Class<*>, vararg typeArguments: Class<*>): T? =
  JsonUtil.fromJson(this, rawType, *typeArguments)

fun Any?.toJson(): String? = JsonUtil.toJson(this)
