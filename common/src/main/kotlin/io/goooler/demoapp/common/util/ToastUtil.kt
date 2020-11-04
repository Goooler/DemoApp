@file:Suppress("unused")

package io.goooler.demoapp.common.util

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import android.widget.Toast
import androidx.annotation.AnyThread
import androidx.annotation.StringRes
import androidx.annotation.UiThread
import androidx.annotation.WorkerThread
import io.goooler.demoapp.base.core.BaseApplication

/**
 * Toast 简单封装
 */
@SuppressLint("WrongThread")
object ToastUtil {

    private var toast: Toast? = null

    @AnyThread
    fun show(context: Context, @StringRes strResId: Int) {
        show(context, context.getString(strResId))
    }

    /**
     * 可在子线程使用的 toast
     *
     * @param text string 文本
     */
    @AnyThread
    fun show(context: Context, text: String) {
        if (Looper.getMainLooper().thread === Thread.currentThread()) {
            showToastInMainThread(context, text)
        } else {
            showToastInWorkerThread(context, text)
        }
    }

    @WorkerThread
    fun showToastInWorkerThread(context: Context, @StringRes strResId: Int) {
        showToastInWorkerThread(context, context.getString(strResId))
    }

    @WorkerThread
    fun showToastInWorkerThread(context: Context, text: String) {
        Looper.prepare()
        showToastInMainThread(context, text)
        Looper.loop()
    }

    @UiThread
    fun showToastInMainThread(context: Context, @StringRes strResId: Int) {
        showToastInMainThread(context, context.getString(strResId))
    }

    /**
     * 只在主线程调用，真正实现 toast 的方法
     */
    @UiThread
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

@AnyThread
fun @receiver:StringRes Int.showToast() {
    ToastUtil.show(BaseApplication.app, this)
}

@AnyThread
fun String.showToast() {
    ToastUtil.show(BaseApplication.app, this)
}