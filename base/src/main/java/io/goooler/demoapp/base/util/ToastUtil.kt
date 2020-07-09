package io.goooler.demoapp.base.util

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import android.widget.Toast
import androidx.annotation.AnyThread
import androidx.annotation.MainThread


/**
 * Toast 简单封装
 */
object ToastUtil {
    private val isMainThread = Looper.getMainLooper().thread === Thread.currentThread()

    private var toast: Toast? = null

    /**
     * 可在子线程使用的 toast
     *
     * @param text string 文本
     */
    @SuppressLint("WrongThread")
    @AnyThread
    fun showToastInAnyThread(context: Context, text: String) {
        if (isMainThread) {
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
    @SuppressLint("ShowToast")
    @MainThread
    @Synchronized
    fun showToastInMainThread(context: Context, text: String) {
        // 把上一条先置空，再显示下一条
        if (toast != null) {
            toast!!.cancel()
            toast = null
        }
        toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast!!.show()
    }
}
