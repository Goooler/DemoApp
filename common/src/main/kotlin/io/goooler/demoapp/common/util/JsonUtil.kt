@file:Suppress("unused")

package io.goooler.demoapp.common.util

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

object JsonUtil {
  @PublishedApi
  internal val moshi: Moshi = Moshi.Builder().build()

  inline fun <reified T> fromJson(string: String): T? = try {
    moshi.adapter(T::class.java).fromJson(string)
  } catch (e: Exception) {
    e.printStackTrace()
    null
  }

  fun <T> fromJson(
    string: String,
    rawType: Class<*>,
    vararg typeArguments: Class<*>
  ): T? = try {
    moshi.adapter<T>(Types.newParameterizedType(rawType, *typeArguments)).fromJson(string)
  } catch (e: Exception) {
    e.printStackTrace()
    null
  }

  inline fun <reified T> toJson(value: T?): String? = try {
    moshi.adapter(T::class.java).toJson(value)
  } catch (e: Exception) {
    e.printStackTrace()
    null
  }
}

inline fun <reified T> String.fromJson(): T? = JsonUtil.fromJson(this, T::class.java)

inline fun <reified T> String.fromJson(rawType: Class<*>, vararg typeArguments: Class<*>): T? =
  JsonUtil.fromJson(this, rawType, *typeArguments)

fun Any?.toJson(): String? = JsonUtil.toJson(this)
