package io.goooler.demoapp.util;

import android.content.SharedPreferences;

import io.goooler.demoapp.base.BaseApplication;
import io.goooler.demoapp.model.Constants;

import static android.content.Context.MODE_PRIVATE;

/**
 * SharedPreferences 简单封装
 */

public class SpUtil {

    /**
     * 获取 sp
     *
     * @param spName sp 文件名
     */
    public static SharedPreferences getSp(String spName) {
        return BaseApplication.getContext().getSharedPreferences(spName, MODE_PRIVATE);
    }

    /**
     * 获取 sp edit
     *
     * @param spName sp 文件名
     */
    public static SharedPreferences.Editor getSpEdit(String spName) {
        return getSp(spName).edit();
    }

    /**
     * 设置应用是否第一次启动的状态
     */
    public static void setFirstRunState(boolean firstRunState) {
        getSpEdit(Constants.SP_RUNINFO).putBoolean(Constants.SP_FIRST_RUN, firstRunState).apply();
    }

    /**
     * 判断应用是否第一次启动
     */
    public static boolean isFirstRun() {
        return getSp(Constants.SP_RUNINFO).getBoolean(Constants.SP_FIRST_RUN, true);
    }
}
