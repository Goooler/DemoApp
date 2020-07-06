package io.goooler.demoapp.base.util.device;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.annotation.Px;

/**
 * @author JokerWan
 * Created by JokerWan on 2018/7/13.
 * Fix by feling on 2019/08/17.
 * Function: dp转px   px转dp  sp转px px转sp
 */

public class DimensionUtil {

    private DimensionUtil() {
        throw new UnsupportedOperationException("you can't instantiate me.");
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context .
     * @param dpValue .
     * @return pxValue
     */
    @Px
    public static int dp2px(@NonNull Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpValue, context.getResources().getDisplayMetrics());
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context .
     * @param pxValue .
     * @return dpValue
     */
    public static float px2dp(@NonNull Context context, int pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxValue / scale);
    }

    /**
     * sp 转 px
     *
     * @param context .
     * @param spValue .
     * @return pxValue
     */
    @Px
    public static int sp2px(@NonNull Context context, float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spValue, context.getResources().getDisplayMetrics());
    }

    /**
     * px 转 sp
     *
     * @param context .
     * @param pxValue .
     * @return spValue
     */
    public static float px2sp(@NonNull Context context, int pxValue) {
        return (pxValue / context.getResources().getDisplayMetrics().scaledDensity);
    }

    /**
     * pt 转 px
     *
     * @param context 目前pt适配需要传入activity的context
     * @param ptValue .
     * @return .
     */
    @Px
    public static int pt2px(@NonNull Context context, final float ptValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) (ptValue * metrics.xdpi / 72f + 0.5f);
    }

    /**
     * px 转 pt
     *
     * @param context 目前pt适配需要传入activity的context
     * @param pxValue .
     * @return .
     */
    public static float px2pt(@NonNull Context context, final int pxValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (pxValue * 72f / metrics.xdpi + 0.5f);
    }
}