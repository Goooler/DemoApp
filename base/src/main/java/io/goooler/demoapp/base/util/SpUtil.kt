package io.goooler.demoapp.base.util

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import io.goooler.demoapp.base.core.BaseApplication
import io.goooler.demoapp.base.type.SpKeys

/**
 * SharedPreferences 简单封装
 */

object SpUtil {

    private const val SP_CONFIG = "sp_config"

    fun getBoolean(@SpKeys key: String, default: Boolean = false): Boolean {
        return getSp().getBoolean(key, default)
    }

    fun putBoolean(@SpKeys key: String, value: Boolean = true) {
        getSpEditor {
            putBoolean(key, value)
        }
    }

    fun getLong(@SpKeys key: String, default: Long = 0): Long {
        return getSp().getLong(key, default)
    }

    fun putLong(@SpKeys key: String, value: Long) {
        getSpEditor {
            putLong(key, value)
        }
    }

    fun getFloat(@SpKeys key: String, default: Float = 0f): Float {
        return getSp().getFloat(key, default)
    }

    fun putFloat(@SpKeys key: String, value: Float) {
        getSpEditor {
            putFloat(key, value)
        }
    }

    fun getString(@SpKeys key: String, default: String? = null): String? {
        return getSp().getString(key, default)
    }

    fun putString(@SpKeys key: String, value: String) {
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
