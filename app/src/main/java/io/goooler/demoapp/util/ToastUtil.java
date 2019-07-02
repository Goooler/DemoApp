package io.goooler.demoapp.util;

import android.widget.Toast;

import androidx.annotation.StringRes;

import io.goooler.demoapp.base.BaseApplication;


/**
 * Toast 简单封装
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
     * @param stringId 文本资源 id
     */
    public static void showToast(@StringRes int stringId) {
        showToast(ResUtil.getString(stringId));
    }
}
