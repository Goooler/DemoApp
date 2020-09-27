@file:Suppress("unused")

package io.goooler.demoapp.base.util

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import android.widget.Toast
import androidx.annotation.AnyThread
import androidx.annotation.StringRes
import androidx.annotation.UiThread
import androidx.annotation.WorkerThread

/**
 * Toast 简单封装
 */
@SuppressLint("WrongThread")
object ToastUtil {

    private var toast: Toast? = null

    @AnyThread
    fun showToastInAnyThread(context: Context, @StringRes strResId: Int) {
        showToastInAnyThread(context, context.getString(strResId))
    }

    /**
     * 可在子线程使用的 toast
     *
     * @param text string 文本
     */
    @AnyThread
    fun showToastInAnyThread(context: Context, text: String) {
        if (isMainThread) {
            showToastInMainThread(context, text)
        } else {
            showToastInWorkerThread(context, text)
        }
    }

    @WorkerThread
    fun showToastInWorkerThread(context: Context, text: String) {
        Looper.prepare()
        showToastInMainThread(context, text)
        Looper.loop()
    }

    @WorkerThread
    fun showToastInWorkerThread(context: Context, @StringRes strResId: Int) {
        Looper.prepare()
        showToastInMainThread(context, strResId)
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

@UiThread
fun String.showToastInMainThread(context: Context) {
    ToastUtil.showToastInAnyThread(context, this)
}

@WorkerThread
fun String.showToastInWorkerThread(context: Context) {
    ToastUtil.showToastInWorkerThread(context, this)
}

@AnyThread
fun String.showToastInAnyThread(context: Context) {
    ToastUtil.showToastInAnyThread(context, this)
}

@UiThread
fun @receiver:StringRes Int.showToastInMainThread(context: Context) {
    ToastUtil.showToastInAnyThread(context, this)
}

@WorkerThread
fun @receiver:StringRes Int.showToastInWorkerThread(context: Context) {
    ToastUtil.showToastInWorkerThread(context, this)
}

@AnyThread
fun @receiver:StringRes Int.showToastInAnyThread(context: Context) {
    ToastUtil.showToastInAnyThread(context, this)
}