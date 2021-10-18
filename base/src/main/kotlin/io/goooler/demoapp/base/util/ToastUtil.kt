@file:Suppress("unused", "WrongThread")

package io.goooler.demoapp.base.util

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.AnyThread
import androidx.annotation.MainThread
import androidx.annotation.StringRes
import java.lang.ref.WeakReference

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
    if (Looper.getMainLooper() == Looper.myLooper()) {
      showInMain(context, text)
    } else {
      handler.post { showInMain(context, text) }
    }
  }

  @MainThread
  fun showInMain(context: Context, @StringRes strResId: Int) {
    showInMain(context, context.getString(strResId))
  }

  /**
   * 只在主线程调用，真正实现 toast 的方法
   */
  @MainThread
  @Synchronized
  fun showInMain(context: Context, text: String) {
    // 把上一条先置空，再显示下一条
    toast?.get()?.cancel()
    toast = null
    Toast.makeText(context, text, Toast.LENGTH_SHORT).also {
      toast = WeakReference(it)
    }.show()
  }
}
