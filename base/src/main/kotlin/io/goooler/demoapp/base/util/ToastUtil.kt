@file:Suppress("unused")

package io.goooler.demoapp.base.util

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.AnyThread
import androidx.annotation.StringRes
import androidx.annotation.UiThread
import androidx.annotation.WorkerThread
import java.lang.ref.WeakReference

@SuppressLint("WrongThread", "ShowToast")
object ToastUtil {

  private val handler = Handler(Looper.getMainLooper())
  private var toast: WeakReference<Toast?>? = null

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
      showInMainThread(context, text)
    } else {
      showInWorkerThread(context, text)
    }
  }

  @WorkerThread
  fun showInWorkerThread(context: Context, @StringRes strResId: Int) {
    showInWorkerThread(context, context.getString(strResId))
  }

  @WorkerThread
  fun showInWorkerThread(context: Context, text: String) {
    handler.post { showInMainThread(context, text) }
  }

  @UiThread
  fun showInMainThread(context: Context, @StringRes strResId: Int) {
    showInMainThread(context, context.getString(strResId))
  }

  /**
   * 只在主线程调用，真正实现 toast 的方法
   */
  @UiThread
  @Synchronized
  fun showInMainThread(context: Context, text: String) {
    // 把上一条先置空，再显示下一条
    toast?.get()?.cancel()
    toast = null
    Toast.makeText(context, text, Toast.LENGTH_SHORT).also {
      it.show()
      toast = WeakReference(it)
    }
  }
}
