package io.goooler.demoapp.util

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import io.goooler.demoapp.base.BaseApplication
import io.goooler.demoapp.model.SpKeys

/**
 * SharedPreferences 简单封装
 */

object SpUtil {

    private const val SP_CONFIG = "sp_config"

    /**
     * 判断应用是否第一次启动
     */
    val isFirstRun = getSp().getBoolean(SpKeys.SP_FIRST_RUN, true)

    /**
     * 设置应用是否第一次启动的状态
     */
    fun setFirstRunState(state: Boolean = false) {
        putBoolean(SpKeys.SP_FIRST_RUN, state)
    }

    fun getBoolean(@SpKeys spKey: String, default: Boolean = false): Boolean {
        return getSp().getBoolean(spKey, default)
    }

    fun putBoolean(@SpKeys spKey: String, value: Boolean = true) {
        getSp().edit().putBoolean(spKey, value).apply()
    }

    /**
     * 获取 sp
     *
     * @param spName sp 文件名
     */
    private fun getSp(spName: String = SP_CONFIG): SharedPreferences {
        return BaseApplication.context.getSharedPreferences(spName, MODE_PRIVATE)
    }
}
