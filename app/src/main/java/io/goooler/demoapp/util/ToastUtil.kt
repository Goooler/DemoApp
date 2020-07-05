package io.goooler.demoapp.util

import android.content.Context
import android.os.Looper
import android.widget.Toast
import androidx.annotation.AnyThread
import androidx.annotation.MainThread


/**
 * Toast 简单封装
 */
object ToastUtil {

    /**
     * 可在子线程使用的 toast
     *
     * @param text string 文本
     */
    @AnyThread
    fun showToastInAnyThread(context: Context, text: String) {
        if (isMainThread()) {
            showToastInMainThread(context, text)
        } else {
            Looper.prepare()
            showToastInMainThread(context, text)
            Looper.loop()
        }
    }

    /**
     * 只在主线程调用
     */
    @MainThread
    fun showToastInMainThread(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    private fun isMainThread(): Boolean {
        return Looper.getMainLooper().thread === Thread.currentThread()
    }
}
