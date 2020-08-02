@file:Suppress("UNUSED_PARAMETER", "unused")

package io.goooler.demoapp.common.util

import com.tencent.mmkv.MMKV
import io.goooler.demoapp.common.type.MkKeys

typealias MMKVUtil = MkUtil

object MkUtil {
    private const val MK_CONFIG = "mk_config"

    fun getBoolean(@MkKeys key: String, default: Boolean = false): Boolean {
        return getMk()
            .decodeBool(key, default)
    }

    fun putBoolean(@MkKeys key: String, value: Boolean = true) {
        getMk().encode(key, value)
    }

    fun getLong(@MkKeys key: String, default: Long = 0): Long {
        return getMk()
            .decodeLong(key, default)
    }

    fun putLong(@MkKeys key: String, value: Long) {
        getMk().encode(key, value)
    }

    fun getDouble(@MkKeys key: String, default: Double = 0.0): Double {
        return getMk()
            .decodeDouble(key, default)
    }

    fun putDouble(@MkKeys key: String, value: Double) {
        getMk().encode(key, value)
    }

    fun getString(@MkKeys key: String, default: String? = null): String? {
        return getMk()
            .decodeString(key, default)
    }

    fun putString(@MkKeys key: String, value: String) {
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