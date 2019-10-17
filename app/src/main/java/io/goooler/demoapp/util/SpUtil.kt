package io.goooler.demoapp.util

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import io.goooler.demoapp.base.BaseApplication
import io.goooler.demoapp.model.Constants

/**
 * SharedPreferences 简单封装
 */

object SpUtil {

    /**
     * 判断应用是否第一次启动
     */
    val isFirstRun: Boolean
        get() = getSp(Constants.SpKey.SP_RUN_INFO).getBoolean(Constants.SpKey.SP_FIRST_RUN, true)

    /**
     * 获取 sp
     *
     * @param spName sp 文件名
     */
    fun getSp(spName: String): SharedPreferences {
        return BaseApplication.context!!.getSharedPreferences(spName, MODE_PRIVATE)
    }

    /**
     * 获取 sp edit
     *
     * @param spName sp 文件名
     */
    fun getSpEdit(spName: String): SharedPreferences.Editor {
        return getSp(spName).edit()
    }

    /**
     * 设置应用是否第一次启动的状态
     */
    fun setFirstRunState(firstRunState: Boolean) {
        getSpEdit(Constants.SpKey.SP_RUN_INFO).putBoolean(Constants.SpKey.SP_FIRST_RUN, firstRunState).apply()
    }
}
