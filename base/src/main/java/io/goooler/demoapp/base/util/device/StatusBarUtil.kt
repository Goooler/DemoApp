package io.goooler.demoapp.base.util.device

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.Px

/**
 * Fix by feling on 2019/08/17.
 *
 * @author liyanfang
 * @date 2018/8/31
 * 状态栏修改
 */
@SuppressLint("ObsoleteSdkInt")
object StatusBarUtil {
    /**
     * 设置沉浸式状态栏
     *
     * @param activity  .
     * @param statusPad .
     */
    fun setImmerseStatusBarLightMode(
        activity: Activity,
        statusPad: View?
    ) {
        setStatusBarTransparent(activity)
        setStatusBarLight(activity, statusPad)
    }

    /**
     * 设置沉浸式状态栏
     *
     * @param activity  .
     * @param statusPad .
     */
    fun setImmerseStatusBarDarkMode(
        activity: Activity,
        statusPad: View?
    ) {
        setStatusBarTransparent(activity)
        setStatusBarDark(activity, statusPad)
    }

    /**
     * 设置状态栏浅色模式（状态栏文字黑色）
     *
     * @param activity activity
     */
    private fun setStatusBarLight(
        activity: Activity,
        statusPad: View?
    ) {
        val isChange = setStatusBarColorBlack(activity)
        if (isChange) {
            return
        }
        statusPad?.setBackgroundColor(Color.WHITE)
    }

    /**
     * 设置状态栏深色模式（状态栏文字白色）
     *
     * @param activity  activity
     * @param statusPad .
     */
    private fun setStatusBarDark(
        activity: Activity,
        statusPad: View?
    ) {
        val isChange = setStatusBarColorWhite(activity)
        if (isChange) {
            return
        }
        statusPad?.setBackgroundColor(Color.BLACK)
    }

    /**
     * 设置状态栏文字颜色及图标为黑色
     */
    fun setStatusBarColorBlack(activity: Activity): Boolean {
        val window = activity.window
        if (isMIUISetStatusBarLightMode(window, true)) {
            return true
        } else if (isFlymeSetStatusBarLightMode(window, true)) {
            return true
        }
        return setAndroid6StatusBar(window, true)
    }

    /**
     * 设置状态栏文字颜色及图标为白色
     */
    fun setStatusBarColorWhite(activity: Activity): Boolean {
        val window = activity.window
        if (isMIUISetStatusBarLightMode(window, false)) {
            return true
        } else if (isFlymeSetStatusBarLightMode(window, false)) {
            return true
        }
        return setAndroid6StatusBar(window, false)
    }

    /**
     * 沉浸模式的状态栏
     *
     * @param activity 上下文
     */
    fun setStatusBarTransparent(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity.window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        } else {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    /**
     * 设置6.0后的状态颜色
     *
     * @param window            当前窗体
     * @param isUpdateFontColor 是否改变颜色
     */
    private fun setAndroid6StatusBar(
        window: Window?,
        isUpdateFontColor: Boolean
    ): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && window != null) {
            if (isUpdateFontColor) {
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
            return true
        }
        return false
    }

    /**
     * 设置状态栏字体图标为深色，需要 MIUI V6 以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    @SuppressLint("PrivateApi")
    private fun isMIUISetStatusBarLightMode(
        window: Window?,
        dark: Boolean
    ): Boolean {
        var result = false
        if (window != null) {
            val clazz: Class<*> = window.javaClass
            try {
                val darkModeFlag: Int
                val layoutParams =
                    Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                val field =
                    layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                darkModeFlag = field.getInt(layoutParams)
                val extraFlagField = clazz.getMethod(
                    "setExtraFlags",
                    Int::class.javaPrimitiveType,
                    Int::class.javaPrimitiveType
                )
                setAndroid6StatusBar(window, dark)
                if (dark) {
                    //状态栏透明且黑色字体
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag)
                } else {
                    //清除黑色字体
                    extraFlagField.invoke(window, 0, darkModeFlag)
                }
                result = true
            } catch (e: Exception) {
            }
        }
        return result
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为 Flyme 用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private fun isFlymeSetStatusBarLightMode(
        window: Window?,
        dark: Boolean
    ): Boolean {
        var result = false
        if (window != null) {
            try {
                val lp = window.attributes
                val darkFlag =
                    WindowManager.LayoutParams::class.java.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
                val meizuFlags =
                    WindowManager.LayoutParams::class.java.getDeclaredField("meizuFlags")
                darkFlag.isAccessible = true
                meizuFlags.isAccessible = true
                val bit = darkFlag.getInt(null)
                var value = meizuFlags.getInt(lp)
                value = if (dark) {
                    value or bit
                } else {
                    value and bit.inv()
                }
                meizuFlags.setInt(lp, value)
                window.attributes = lp
                result = true
            } catch (e: Exception) {
            }
        }
        return result
    }

    /**
     * 得到状态栏的高度
     *
     * @param context context
     * @return 高度（int类型）
     */
    @Px
    fun getStatusBarHeight(context: Context?): Int {
        var result = 0
        if (context != null) {
            val resId =
                context.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resId > 0) {
                result = context.resources.getDimensionPixelOffset(resId)
            }
        }
        return result
    }
}