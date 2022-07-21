@file:Suppress("unused")

package io.goooler.demoapp.obsolete.util

import com.google.gson.Gson
import java.lang.reflect.Type

sealed interface JsonConverter {

  fun <T> fromJson(json: String, classOfT: Class<T>): T?

  fun <T> fromJson(json: String, typeOfT: Type): T?

  fun toJson(src: Any): String?
}

interface GsonUtil : JsonConverter {

  companion object : GsonUtil {
    private val gson = Gson()

    override fun <T> fromJson(json: String, classOfT: Class<T>): T? = try {
      gson.fromJson(json, classOfT)
    } catch (e: Exception) {
      e.printStackTrace()
      null
    }

    override fun <T> fromJson(json: String, typeOfT: Type): T? = try {
      gson.fromJson(json, typeOfT)
    } catch (e: Exception) {
      e.printStackTrace()
      null
    }

    override fun toJson(src: Any): String? = try {
      gson.toJson(src)
    } catch (e: Exception) {
      e.printStackTrace()
      null
    }
  }
}
