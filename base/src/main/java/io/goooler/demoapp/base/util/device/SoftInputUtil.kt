package io.goooler.demoapp.base.util.device

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.inputmethod.InputMethodManager
import java.util.*

/**
 * 键盘（输入法软键盘）操作
 */
object SoftInputUtil {

    private val MAP = WeakHashMap<Activity, OnGlobalLayoutListener>()

    /**
     * 记录根视图的显示高度
     */
    private var rootViewVisibleHeight = 0

    /**
     * 显示键盘。 自动寻找焦点的 View
     *
     * @param activity .
     */
    fun showSoftInput(activity: Activity) {
        getInputMethodManager(activity)?.showSoftInput(
            activity.currentFocus,
            InputMethodManager.SHOW_FORCED
        )
    }

    /**
     * 显示键盘
     *
     * @param view 获取焦点的 view，一般是 editText 或其子类
     */
    fun showSoftInput(view: View) {
        getInputMethodManager(view)?.showSoftInput(
            view,
            InputMethodManager.SHOW_FORCED
        )
    }

    /**
     * 隐藏键盘
     *
     * @param activity activity
     */
    fun hideSoftInput(activity: Activity) {
        getInputMethodManager(activity)?.let {
            if (activity.currentFocus?.windowToken != null) {
                it.hideSoftInputFromWindow(
                    activity.currentFocus!!.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }
    }

    /**
     * 隐藏键盘
     *
     * @param view 获取焦点的view。一般是editText或其子类
     */
    fun hideSoftInput(view: View) {
        getInputMethodManager(view)?.hideSoftInputFromWindow(
            view.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    /**
     * 如果输入法打开则关闭，如果没打开则打开。
     *
     * @param activity .
     */
    fun toggleSoftInput(activity: Activity) {
        getInputMethodManager(activity)?.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    /**
     * 如果输入法打开则关闭，如果没打开则打开。
     *
     * @param view .
     */
    fun toggleSoftInput(view: View) {
        getInputMethodManager(view)?.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    /**
     * Is keyboard showing boolean.
     *
     * @param activity .
     * @return the boolean
     */
    fun isSoftInputShowing(activity: Activity): Boolean {
        // 获取当前屏幕内容的高度
        val screenHeight = activity.window.decorView.height
        // 获取 View 可见区域的 bottom
        val rect = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(rect)
        return screenHeight - rect.bottom - getSoftButtonsBarHeight(
            activity
        ) != 0
    }

    private fun getSoftButtonsBarHeight(activity: Activity): Int {
        val metrics = DisplayMetrics()
        // 这个方法获取可能不是真实屏幕的高度
        activity.windowManager.defaultDisplay.getMetrics(metrics)
        val usableHeight = metrics.heightPixels
        // 获取当前屏幕的真实高度
        activity.windowManager.defaultDisplay.getRealMetrics(metrics)
        val realHeight = metrics.heightPixels
        return if (realHeight > usableHeight) {
            realHeight - usableHeight
        } else {
            0
        }
    }

    private fun getInputMethodManager(view: View): InputMethodManager? {
        return getInputMethodManager(view.context)
    }

    private fun getInputMethodManager(context: Context): InputMethodManager? {
        return context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    }
}