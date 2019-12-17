package io.goooler.demoapp.util

import android.os.Looper
import android.widget.Toast

import androidx.annotation.StringRes

import io.goooler.demoapp.base.BaseApplication


/**
 * Toast 简单封装
 */
object ToastUtil {

    /**
     * 可在子线程使用的 toast
     *
     * @param text string 文本
     */
    fun showToast(text: String) {
        if (isMainThread()) {
            Toast.makeText(BaseApplication.context, text, Toast.LENGTH_SHORT).show()
        } else {
            Looper.prepare()
            Toast.makeText(BaseApplication.context, text, Toast.LENGTH_SHORT).show()
            Looper.loop()
        }
    }

    /**
     * 默认长度 Toast.LENGTH_SHORT
     *
     * @param stringId 文本资源 id
     */
    fun showToast(@StringRes stringId: Int) {
        showToast(ResUtil.getString(stringId))
    }

    private fun isMainThread(): Boolean {
        return Looper.getMainLooper().thread === Thread.currentThread()
    }
}
