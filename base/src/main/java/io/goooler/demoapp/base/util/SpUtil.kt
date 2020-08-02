@file:Suppress("unused")

package io.goooler.demoapp.base.util

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import io.goooler.demoapp.base.core.BaseApplication

/**
 * SharedPreferences 简单封装
 */
typealias PrefUtil = SpUtil

object SpUtil {

    private const val SP_CONFIG = "sp_config"

    fun getBoolean(key: String, default: Boolean = false): Boolean {
        return getSp()
            .getBoolean(key, default)
    }

    fun putBoolean(key: String, value: Boolean = true) {
        getSpEditor {
            putBoolean(key, value)
        }
    }

    fun getLong(key: String, default: Long = 0): Long {
        return getSp()
            .getLong(key, default)
    }

    fun putLong(key: String, value: Long) {
        getSpEditor {
            putLong(key, value)
        }
    }

    fun getFloat(key: String, default: Float = 0f): Float {
        return getSp()
            .getFloat(key, default)
    }

    fun putFloat(key: String, value: Float) {
        getSpEditor {
            putFloat(key, value)
        }
    }

    fun getString(key: String, default: String? = null): String? {
        return getSp()
            .getString(key, default)
    }

    fun putString(key: String, value: String) {
        getSpEditor {
            putString(key, value)
        }
    }

    /**
     * 获取 sp
     *
     * @param spName sp 文件名
     */
    private fun getSp(spName: String = SP_CONFIG): SharedPreferences {
        return BaseApplication.context.getSharedPreferences(spName, MODE_PRIVATE)
    }

    private inline fun getSpEditor(
        spName: String = SP_CONFIG,
        body: SharedPreferences.Editor.() -> SharedPreferences.Editor
    ) {
        BaseApplication.context.getSharedPreferences(spName, MODE_PRIVATE).edit().body().apply()
    }
}
