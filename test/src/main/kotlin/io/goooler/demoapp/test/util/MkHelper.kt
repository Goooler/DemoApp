@file:Suppress("UNUSED_PARAMETER", "unused")

package io.goooler.demoapp.test.util

import com.tencent.mmkv.MMKV
import java.util.concurrent.ConcurrentHashMap

class MkHelper private constructor(name: String) {
  private val mk: MMKV

  init {
    MMKV.initialize(name)
    mk = MMKV.defaultMMKV() ?: throw IllegalStateException("call MMKV.initialize() first.")
  }

  fun getBoolean(key: String, default: Boolean = false): Boolean = mk.decodeBool(key, default)

  fun putBoolean(key: String, value: Boolean = true) {
    mk.encode(key, value)
  }

  fun getLong(key: String, default: Long = 0): Long = mk.decodeLong(key, default)

  fun putLong(key: String, value: Long) {
    mk.encode(key, value)
  }

  fun getDouble(key: String, default: Double = 0.0): Double = mk.decodeDouble(key, default)

  fun putDouble(key: String, value: Double) {
    mk.encode(key, value)
  }

  fun getString(key: String, default: String? = null): String? = mk.decodeString(key, default)

  fun putString(key: String, value: String) {
    mk.encode(key, value)
  }

  companion object {
    private const val MK_CONFIG = "mk_config"
    private val helperMap = ConcurrentHashMap<String, MkHelper>()

    fun getInstance(name: String): MkHelper {
      return helperMap[name] ?: MkHelper(name).also {
        helperMap[name] = it
      }
    }
  }
}
