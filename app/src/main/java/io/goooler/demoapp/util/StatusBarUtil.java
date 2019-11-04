package io.goooler.demoapp.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Fix by feling on 2019/08/17.
 *
 * @author liyanfang
 * @date 2018/8/31
 * 状态栏修改
 */

public class StatusBarUtil {

    private StatusBarUtil() {
        throw new UnsupportedOperationException("you can't instantiate me.");
    }

    /**
     * 设置沉浸式状态栏
     *
     * @param activity  .
     * @param statusPad .
     */
    public static void setImmerseStatusBarLightMode(@NonNull Activity activity, @Nullable View statusPad) {
        StatusBarUtil.setStatusBarTransparent(activity);
        StatusBarUtil.setStatusBarLight(activity, statusPad);
    }

    /**
     * 设置沉浸式状态栏
     *
     * @param activity  .
     * @param statusPad .
     */
    public static void setImmerseStatusBarDarkMode(@NonNull Activity activity, @Nullable View statusPad) {
        StatusBarUtil.setStatusBarTransparent(activity);
        StatusBarUtil.setStatusBarDark(activity, statusPad);
    }

    /**
     * 设置状态栏浅色模式（状态栏文字黑色）
     *
     * @param activity activity
     */
    private static void setStatusBarLight(@NonNull Activity activity, @Nullable View statusPad) {
        boolean isChange = setStatusBarColorBlack(activity);
        if (isChange) {
            return;
        }
        if (statusPad != null) {
            statusPad.setBackgroundColor(Color.WHITE);
        }
    }

    /**
     * 设置状态栏深色模式（状态栏文字白色）
     *
     * @param activity  activity
     * @param statusPad .
     */
    private static void setStatusBarDark(@NonNull Activity activity, @Nullable View statusPad) {
        boolean isChange = setStatusBarColorWhite(activity);
        if (isChange) {
            return;
        }

        if (statusPad != null) {
            statusPad.setBackgroundColor(Color.BLACK);
        }
    }

    /**
     * 设置状态栏文字颜色及图标为黑色
     */
    public static boolean setStatusBarColorBlack(@NonNull Activity activity) {
        Window window = activity.getWindow();
        if (isMIUISetStatusBarLightMode(window, true)) {
            return true;
        } else if (isFlymeSetStatusBarLightMode(window, true)) {
            return true;
        }

        return setAndroid6StatusBar(window, true);
    }

    /**
     * 设置状态栏文字颜色及图标为白色
     */
    public static boolean setStatusBarColorWhite(@NonNull Activity activity) {
        Window window = activity.getWindow();
        if (isMIUISetStatusBarLightMode(window, false)) {
            return true;
        } else if (isFlymeSetStatusBarLightMode(window, false)) {
            return true;
        }

        return setAndroid6StatusBar(window, false);
    }

    /**
     * 沉浸模式的状态栏
     *
     * @param activity 上下文
     */
    public static void setStatusBarTransparent(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private static boolean isMIUISetStatusBarLightMode(@Nullable Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                setAndroid6StatusBar(window, dark);
                if (dark) {
                    //状态栏透明且黑色字体
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
                } else {
                    //清除黑色字体
                    extraFlagField.invoke(window, 0, darkModeFlag);
                }
                result = true;
            } catch (Exception e) {
            }
        }
        return result;
    }

    /**
     * 设置6.0后的状态颜色
     *
     * @param window            当前窗体
     * @param isUpdateFontColor 是否改变颜色
     */
    private static boolean setAndroid6StatusBar(Window window, boolean isUpdateFontColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && window != null) {
            if (isUpdateFontColor) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
            return true;
        }
        return false;
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private static boolean isFlymeSetStatusBarLightMode(@Nullable Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {
            }
        }
        return result;
    }

    /**
     * 得到状态栏的高度
     *
     * @param context context
     * @return 高度（int类型）
     */
    public static int getStatusBarHeight(@Nullable Context context) {
        int result = 0;
        if (context != null) {
            int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resId > 0) {
                result = context.getResources().getDimensionPixelOffset(resId);
            }
        }
        return result;
    }
}
