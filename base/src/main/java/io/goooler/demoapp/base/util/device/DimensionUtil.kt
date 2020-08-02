@file:Suppress("unused")

package io.goooler.demoapp.base.util.device

import android.content.Context
import android.util.TypedValue
import androidx.annotation.Px
import io.goooler.demoapp.base.core.BaseApplication

/**
 * @author JokerWan
 * Created by JokerWan on 2018/7/13.
 */
object DimensionUtil {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context .
     * @param dpValue .
     * @return pxValue
     */
    @Px
    fun dp2px(context: Context, dpValue: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpValue, context.resources.displayMetrics
        ).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context .
     * @param pxValue .
     * @return dpValue
     */
    fun px2dp(context: Context, pxValue: Int): Float {
        val scale = context.resources.displayMetrics.density
        return pxValue / scale
    }

    /**
     * sp 转 px
     *
     * @param context .
     * @param spValue .
     * @return pxValue
     */
    @Px
    fun sp2px(context: Context, spValue: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            spValue, context.resources.displayMetrics
        ).toInt()
    }

    /**
     * px 转 sp
     *
     * @param context .
     * @param pxValue .
     * @return spValue
     */
    fun px2sp(context: Context, pxValue: Int): Float {
        return pxValue / context.resources.displayMetrics.scaledDensity
    }

    /**
     * pt 转 px
     *
     * @param context 目前pt适配需要传入activity的context
     * @param ptValue .
     * @return .
     */
    @Px
    fun pt2px(context: Context, ptValue: Float): Int {
        val metrics = context.resources.displayMetrics
        return (ptValue * metrics.xdpi / 72f + 0.5f).toInt()
    }

    /**
     * px 转 pt
     *
     * @param context 目前pt适配需要传入activity的context
     * @param pxValue .
     * @return .
     */
    fun px2pt(context: Context, pxValue: Int): Float {
        val metrics = context.resources.displayMetrics
        return pxValue * 72f / metrics.xdpi + 0.5f
    }
}

@Px
fun Float.sp2px(): Int {
    return DimensionUtil.sp2px(BaseApplication.context, this)
}

@Px
fun Float.dp2px(): Int {
    return DimensionUtil.dp2px(BaseApplication.context, this)
}

@Px
fun Float.pt2px(context: Context): Int {
    return DimensionUtil.pt2px(context, this)
}

fun Int.px2sp(): Float {
    return DimensionUtil.px2sp(BaseApplication.context, this)
}

fun Int.px2dp(): Float {
    return DimensionUtil.px2dp(BaseApplication.context, this)
}

fun Int.px2pt(context: Context): Float {
    return DimensionUtil.px2pt(context, this)
}