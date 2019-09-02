package io.goooler.demoapp.util;

import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.StringRes;

import io.goooler.demoapp.base.BaseApplication;


/**
 * Toast 简单封装
 */

public class ToastUtil {

    /**
     * 可在子线程使用的 toast
     *
     * @param text string 文本
     */
    public static void showToast(String text) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Toast.makeText(BaseApplication.getContext(), text, Toast.LENGTH_SHORT).show();
        } else {
            Looper.prepare();
            Toast.makeText(BaseApplication.getContext(), text, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
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
