package io.goooler.demoapp.util;

import android.widget.Toast;

import io.goooler.demoapp.base.BaseApplication;


/**
 * Toast 显示工具
 */

public class ToastUtil {

    /**
     * 默认长度 Toast.LENGTH_SHORT，
     *
     * @param text string 文本
     */
    public static void showToast(String text) {
        Toast.makeText(BaseApplication.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 默认长度 Toast.LENGTH_SHORT
     *
     * @param stringId 资源文件文本
     */
    public static void showToast(int stringId) {
        Toast.makeText(BaseApplication.getContext(), ResUtil.getString(stringId), Toast.LENGTH_SHORT).show();
    }
}
