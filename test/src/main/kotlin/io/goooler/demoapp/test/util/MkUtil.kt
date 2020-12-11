@file:Suppress("UNUSED_PARAMETER", "unused")

package io.goooler.demoapp.test.util

import com.tencent.mmkv.MMKV

typealias MMKVUtil = MkUtil

object MkUtil {
  private const val MK_CONFIG = "mk_config"

  fun getBoolean(key: String, default: Boolean = false): Boolean {
    return getMk().decodeBool(key, default)
  }

  fun putBoolean(key: String, value: Boolean = true) {
    getMk().encode(key, value)
  }

  fun getLong(key: String, default: Long = 0): Long {
    return getMk().decodeLong(key, default)
  }

  fun putLong(key: String, value: Long) {
    getMk().encode(key, value)
  }

  fun getDouble(key: String, default: Double = 0.0): Double {
    return getMk().decodeDouble(key, default)
  }

  fun putDouble(key: String, value: Double) {
    getMk().encode(key, value)
  }

  fun getString(key: String, default: String? = null): String? {
    return getMk().decodeString(key, default)
  }

  fun putString(key: String, value: String) {
    getMk().encode(key, value)
  }

  /**
   * 获取 mmkv
   * @param mkName mk 文件名
   */
  private fun getMk(mkName: String = MK_CONFIG): MMKV {
    return MMKV.defaultMMKV()
  }
}
