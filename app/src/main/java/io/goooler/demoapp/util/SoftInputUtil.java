package io.goooler.demoapp.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;

import java.util.WeakHashMap;

/**
 * Fix by feling on 2019/08/17.
 * 键盘（输入法软键盘）操作
 *
 * @author Ogiso
 */
public class SoftInputUtil {

    private SoftInputUtil() {
        throw new UnsupportedOperationException("you can't instantiate me.");
    }

    /**
     * 显示键盘。 自动寻找焦点的View
     *
     * @param activity .
     */
    public static void showSoftInput(@NonNull Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            boolean b = imm.showSoftInput(activity.getCurrentFocus(), InputMethodManager.SHOW_FORCED);
        } else {
            //LogUtil.d("method showSoftInput: imm != null is false");
        }
    }

    /**
     * 显示键盘
     *
     * @param view 获取焦点的view。一般是editText或其子类
     */
    public static void showSoftInput(@NonNull View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            boolean b = imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            //LogUtil.d("method showSoftInput return " + b);
        } else {
            //LogUtil.d("method showSoftInput: imm != null is false");
        }
    }

    /**
     * 隐藏键盘
     *
     * @param activity activity
     */
    public static void hideSoftInput(@NonNull Activity activity) {
        // 拿到InputMethodManager
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        // 如果window上view获取焦点 && view不为空
        if (imm != null && imm.isActive() && activity.getCurrentFocus() != null) {
            // 拿到view的token 不为空
            if (activity.getCurrentFocus().getWindowToken() != null) {
                // 表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } else {
            //LogUtil.d("method showKeyboard: imm != null && imm.isActive() " +
                    //"&& activity.getCurrentFocus() != null is false");
        }
    }

    /**
     * 隐藏键盘
     *
     * @param view 获取焦点的view。一般是editText或其子类
     */
    public static void hideSoftInput(@NonNull View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            boolean b = imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            //LogUtil.d("method hideSoftInput return " + b);
        } else {
            //LogUtil.d("method hideSoftInput: imm != null is false");
        }
    }

    /**
     * 如果输入法打开则关闭，如果没打开则打开。
     *
     * @param activity .
     */
    public static void toggleSoftInput(@NonNull Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        } else {
            //LogUtil.d("method toggleSoftInput: imm != null is false");
        }
    }

    /**
     * 如果输入法打开则关闭，如果没打开则打开。
     *
     * @param view .
     */
    public static void toggleSoftInput(@NonNull View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        } else {
            //LogUtil.d("method toggleSoftInput: imm != null is false");
        }
    }


    /**
     * Is keyboard showing boolean.
     *
     * @param activity .
     * @return the boolean
     */
    public static boolean isSoftInputShowing(@NonNull Activity activity) {
        // 获取当前屏幕内容的高度
        int screenHeight = activity.getWindow().getDecorView().getHeight();
        // 获取View可见区域的bottom
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

        return screenHeight - rect.bottom - getSoftButtonsBarHeight(activity) != 0;
    }


    private static int getSoftButtonsBarHeight(@NonNull Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        // 这个方法获取可能不是真实屏幕的高度
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        // 获取当前屏幕的真实高度
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }

    public interface OnSoftInputChangeListener {
        void keyBoardShow(int height);

        void keyBoardHide(int height);
    }

    private static WeakHashMap<Activity, ViewTreeObserver.OnGlobalLayoutListener> map = new WeakHashMap<>();

    /**
     * 纪录根视图的显示高度
     */
    private static int rootViewVisibleHeight;


    /**
     * 设置SoftInputChangeListener。
     *
     * @param activity                  .
     * @param onSoftInputChangeListener .
     */
    public static void setListener(@NonNull Activity activity, @NonNull OnSoftInputChangeListener onSoftInputChangeListener) {
        final View rootView = activity.getWindow().getDecorView();

        if (map.get(activity) != null) {
            removeListener(activity);
        }

        ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = () -> {
            //获取当前根视图在屏幕上显示的大小
            Rect r = new Rect();
            rootView.getWindowVisibleDisplayFrame(r);

            int visibleHeight = r.height();
            if (rootViewVisibleHeight == 0) {
                rootViewVisibleHeight = visibleHeight;
                return;
            }

            //根视图显示高度没有变化，可以看作软键盘显示／隐藏状态没有改变
            if (rootViewVisibleHeight == visibleHeight) {
                return;
            }

            //根视图显示高度变小超过200，可以看作软键盘显示了
            if (rootViewVisibleHeight - visibleHeight > 200) {
                onSoftInputChangeListener.keyBoardShow(rootViewVisibleHeight - visibleHeight);
                rootViewVisibleHeight = visibleHeight;
                return;
            }

            //根视图显示高度变大超过200，可以看作软键盘隐藏了
            if (visibleHeight - rootViewVisibleHeight > 200) {
                onSoftInputChangeListener.keyBoardHide(visibleHeight - rootViewVisibleHeight);
                rootViewVisibleHeight = visibleHeight;
            }
        };

        //监听视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);

        map.put(activity, onGlobalLayoutListener);
    }

    /**
     * 移除SoftInputChangeListener
     *
     * @param activity .
     */
    public static void removeListener(@NonNull Activity activity) {
        View rootView = activity.getWindow().getDecorView();
        ViewTreeObserver.OnGlobalLayoutListener listener = map.remove(activity);
        rootView.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
    }

}