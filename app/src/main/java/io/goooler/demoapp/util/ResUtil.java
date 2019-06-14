package io.goooler.demoapp.util;

import android.annotation.SuppressLint;
import android.content.res.Resources;

import io.goooler.demoapp.base.BaseApplication;


/**
 * 获取资源的工具封装，可以在任何控件中直接获取
 */
public class ResUtil {

    public static Resources getResources() {
        return BaseApplication.getContext().getResources();
    }

    public static String getString(int id) {
        return getResources().getString(id);
    }

    public static String[] getStringArray(int id) {
        return getResources().getStringArray(id);
    }

    @SuppressLint("NewApi")
    public static int getColor(int id) {
        return getResources().getColor(id, null);
    }
}
