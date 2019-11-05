package io.goooler.demoapp.util.device;

import android.content.Context;
import android.util.TypedValue;

import androidx.annotation.NonNull;

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
}
