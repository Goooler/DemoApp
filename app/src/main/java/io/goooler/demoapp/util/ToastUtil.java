package io.goooler.demoapp.util;

import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import io.goooler.demoapp.base.BaseApplication;


/**
 * Toast 简单封装
 */
@RequiresApi(api = Build.VERSION_CODES.M)
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
    public static void showToast(int stringId) {
        showToast(ResUtil.getString(stringId));
    }
}
