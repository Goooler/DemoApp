@file:Suppress("unused")

package io.goooler.demoapp.obsolete.util

import com.google.gson.Gson
import java.lang.reflect.Type

object GsonUtil {
  @PublishedApi
  internal val gson = Gson()

  inline fun <reified T> fromJson(json: String, classOfT: Class<T>): T? =
    runCatching { gson.fromJson(json, classOfT) }.getOrNull()

  inline fun <reified T> fromJson(json: String, typeOfT: Type): T? =
    runCatching { gson.fromJson<T>(json, typeOfT) }.getOrNull()

  fun toJson(src: Any): String? = runCatching { gson.toJson(src) }.getOrNull()
}
