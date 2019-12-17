package io.goooler.demoapp.util

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.view.View
import android.webkit.URLUtil
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import io.goooler.demoapp.BuildConfig
import io.goooler.demoapp.base.BaseApplication
import io.goooler.demoapp.model.Constants.IMAGE_URL_PREFIX
import io.goooler.demoapp.model.Constants.PHONE_FIRST_CHAR
import io.goooler.demoapp.model.Constants.PHONE_LENGTH
import io.goooler.demoapp.util.device.DimensionUtil
import java.math.BigDecimal
import java.util.*
import kotlin.math.absoluteValue

//---------------------Log-------------------------------//

fun Any?.log() {
    LogUtil.d(this)
}

//---------------------String-------------------------------//

inline fun <reified T> String.fromJson(): T? {
    return JsonUtil.fromJson(this, T::class.java)
}

fun Any.toJson(): String {
    return JsonUtil.toJson(this)
}

fun String.fromHtml(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(this)
    }
}

fun String.formatRes(@StringRes resId: Int): String {
    return String.format(ResUtil.getString(resId), this)
}

//---------------------Calculate-------------------------------//

/**
 * @param isYuan 默认以分为单位，传入元为单位传 true
 * @param trans2W 是否需要在超过一万时转换为 1.2w 的形式，不需要的话传 false
 *
 * 分是 Long 类型、元是 Double 类型
 */
fun Number.formatMoney(isYuan: Boolean = false, trans2W: Boolean = false, scale: Int = 2): String {
    val moneyF = if (isYuan) {
        this.toDouble()
    } else {
        // 分转为元
        this.toDouble() / 100
    }
    try {
        when {
            trans2W && moneyF / 10000 > 0 -> {
                return BigDecimal.valueOf(moneyF / 10000)
                        .setScale(1, BigDecimal.ROUND_DOWN)
                        .stripTrailingZeros().toPlainString() + "W"
            }

            else ->
                BigDecimal.valueOf(moneyF)
                        .setScale(scale, BigDecimal.ROUND_DOWN)
                        .stripTrailingZeros().toPlainString()
                        .let {
                            return if (it.toDouble().absoluteValue < 0.000001) {
                                "0"
                            } else {
                                it
                            }
                        }

        }
    } catch (e: Exception) {
        return moneyF.toString()
    }
}

// 获取图片宽高比例，如：/assets/img/2019/07/18/n_1563460410803_3849___size550x769.jpg
fun String.getSizeByLoadUrl(defaultWidth: Int, defaultHeight: Int): List<Int> {
    val sizeList = ArrayList<Int>()
    sizeList.add(defaultWidth)
    sizeList.add(defaultHeight)
    val flag = "size"
    if (!this.contains(IMAGE_URL_PREFIX) || !this.contains(flag)) {
        return sizeList
    }
    val pattern = "$flag(\\d+x\\d+)"
    Regex(pattern)
            .findAll(this)
            .forEach {

                // size550x769
                val sizeXXXxXXX = it.value
                // 550x769
                val mXXXxXXX = sizeXXXxXXX.replace(flag, "")
                val list = mXXXxXXX.split("x")

                if (list.size < 2) {
                    return sizeList
                }
                // 550
                val width = list[0].toInt()
                // 769
                val height = list[1].toInt()
                sizeList.clear()
                sizeList.add(width)
                sizeList.add(height)
                return sizeList
            }

    return sizeList
}

infix fun Double.plus(that: Double): Double {
    return (BigDecimal(this.toString()) + BigDecimal(that.toString())).toDouble()
}

infix fun Double.minus(that: Double): Double {
    return (BigDecimal(this.toString()) - BigDecimal(that.toString())).toDouble()
}

infix fun Double.times(that: Double): Double {
    return (BigDecimal(this.toString()) * BigDecimal(that.toString())).toDouble()
}

infix fun Double.div(that: Double): Double {
    return (BigDecimal(this.toString()) / BigDecimal(that.toString())).toDouble()
}

fun Number.isZero(): Boolean {
    return when (this) {
        is Double -> BigDecimal(this.toString()) == BigDecimal("0.0")
        is Long -> this == 0L
        is Short, is Int -> this == 0
        else -> false
    }
}

fun Number.isNotZero(): Boolean {
    return !isZero()
}

//---------------------Date-------------------------------//

fun Long.formatTime(pattern: String): String {
    return DateUtil.timestampToDateString(this, pattern)
}

fun Long.easyTime(): String {
    val now = System.currentTimeMillis()
    val t = now - this
    if (t < 0) {// 未来
        return this.formatTime("yyyy-MM-dd HH:mm")
    }
    val oneMinute = 1000 * 60
    val oneHour = oneMinute * 60
    val oneDay = oneHour * 24
    val c1 = Calendar.getInstance()
    val c2 = Calendar.getInstance()
    c1.time = Date(this)
    c2.time = Date(now)
    val day1 = c1.get(Calendar.DAY_OF_WEEK)
    val day2 = c2.get(Calendar.DAY_OF_WEEK)
    val isYesterday = t < oneDay * 2 && (day2 - day1 == 1 || day2 - day1 == -6)

    val year1 = c1.get(Calendar.YEAR)
    val year2 = c2.get(Calendar.YEAR)

    val isSameYear = year1 == year2


    return when {
        !isSameYear -> this.formatTime("yyyy-MM-dd HH:mm")
        isYesterday -> this.formatTime("昨天 HH:mm")
        t < oneMinute -> "刚刚"
        t < oneHour -> (t / oneMinute).toString() + "分钟前"
        t < oneDay -> (t / oneHour).toString() + "小时前"
        isSameYear -> this.formatTime("MM-dd HH:mm")
        else -> this.formatTime("yyyy-MM-dd HH:mm")
    }
}

//---------------------Network-------------------------------//

fun String.isNetworkUrl(): Boolean {
    return URLUtil.isNetworkUrl(this)
}

fun String.toLoadUrl(): String {
    return if (this.isNetworkUrl()) {
        this
    } else {
        IMAGE_URL_PREFIX + this
    }
}

//---------------------Phone-------------------------------//

fun String.isValidPhoneFormat(): Boolean {
    return this.startsWith(PHONE_FIRST_CHAR) && this.length == PHONE_LENGTH
}

fun String.hidePhone(): String {
    return this.replace(Regex("(\\d{3})\\d{4}(\\d{4})"), "$1****$2")
}

fun Float.sp2px(): Int {
    return DimensionUtil.sp2px(BaseApplication.context, this)
}

fun Float.dp2px(): Int {
    return DimensionUtil.dp2px(BaseApplication.context, this)
}

fun Int.px2sp(): Float {
    return DimensionUtil.px2sp(BaseApplication.context, this)
}

fun Int.px2dp(): Float {
    return DimensionUtil.px2dp(BaseApplication.context, this)
}


//---------------------View-------------------------------//


/**
 * 设置 view 的背景，支持圆形和矩形，渐变色和描边圆角等
 *
 * @param shapeType 背景形状，圆、矩形等
 * @param gradualColors 渐变色数组，和填充色互斥
 * @param angle 渐变色角度
 * @param solidColor 填充色，和渐变色数组互斥
 * @param strokeColor 描边色
 * @param stroke 描边粗细
 * @param radius 圆角大小
 */
fun View.setBgShapeGradual(shapeType: Int = GradientDrawable.RECTANGLE,
                           @ColorInt gradualColors: IntArray? = null,
                           angle: Int = -1,
                           @ColorInt solidColor: Int? = null,
                           @ColorInt strokeColor: Int = Color.TRANSPARENT,
                           stroke: Float = 0f,
                           radius: Float = 0f) {
    background = GradientDrawable().apply {
        shape = shapeType
        useLevel = false
        gradientType = GradientDrawable.LINEAR_GRADIENT
        when (angle) {
            0 -> orientation = GradientDrawable.Orientation.LEFT_RIGHT
            45 -> orientation = GradientDrawable.Orientation.BL_TR
            90 -> orientation = GradientDrawable.Orientation.BOTTOM_TOP
            135 -> orientation = GradientDrawable.Orientation.BR_TL
            180 -> orientation = GradientDrawable.Orientation.RIGHT_LEFT
            225 -> orientation = GradientDrawable.Orientation.TR_BL
            270 -> orientation = GradientDrawable.Orientation.TOP_BOTTOM
            315 -> orientation = GradientDrawable.Orientation.TL_BR
        }
        if (gradualColors != null && solidColor == null) {
            colors = gradualColors
        } else if (gradualColors == null && solidColor != null) {
            setColor(solidColor)
        }
        setStroke(stroke.toInt(), strokeColor)
        cornerRadius = radius
    }
}

/**
 * 设置 view 在矩形某几个角上需要圆角的背景
 *
 * @param solidColor 填充色
 * @param topLeft 左上圆角大小
 * @param topRight 右上圆角大小
 * @param bottomLeft 左下圆角大小
 * @param bottomRight 左下圆角大小
 */
fun View.setBgShapeCorners(@ColorInt solidColor: Int = Color.WHITE,
                           topLeft: Float = 0f,
                           topRight: Float = 0f,
                           bottomLeft: Float = 0f,
                           bottomRight: Float = 0f) {
    background = GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        setColor(solidColor)
        cornerRadii = floatArrayOf(
                topLeft, topLeft,
                topRight, topRight,
                bottomRight, bottomRight,
                bottomLeft, bottomLeft
        )
    }
}

//---------------------SafeTrans-------------------------------//

fun String?.safeToBoolean(default: Boolean = false): Boolean {
    if (this == null) return default
    return try {
        this.toBoolean()
    } catch (e: Throwable) {
        default
    }
}

fun String?.safeToInt(default: Int = 0): Int {
    if (this == null) return default
    return try {
        this.toInt()
    } catch (e: Throwable) {
        default
    }
}

fun String?.safeToLong(default: Long = 0L): Long {
    if (this == null) return default
    return try {
        this.toLong()
    } catch (e: Throwable) {
        default
    }
}

fun String?.safeToFloat(default: Float = 0f): Float {
    if (this == null) return default
    return try {
        this.toFloat()
    } catch (e: Throwable) {
        default
    }
}

fun String?.safeToDouble(default: Double = 0.0): Double {
    if (this == null) return default
    return try {
        this.toDouble()
    } catch (e: Throwable) {
        default
    }
}

@ColorInt
fun String?.safeToColor(@ColorInt default: Int = 0): Int {
    return try {
        Color.parseColor(this)
    } catch (e: Throwable) {
        default
    }
}

//---------------------Func-------------------------------//

fun Boolean.trueRun(whenTrue: () -> Unit = {}): Boolean {
    if (this) {
        whenTrue()
    }
    return this
}

fun Boolean.falseRun(whenFalse: () -> Unit = {}): Boolean {
    if (!this) {
        whenFalse()
    }
    return this
}

fun debugRun(debug: () -> Unit): Boolean {
    if (BuildConfig.DEBUG) {
        debug()
    }
    return BuildConfig.DEBUG
}

//---------------------Collections-------------------------------//

fun <E> MutableCollection<E>.removeIfMatch(predicate: (e: E) -> Boolean): Boolean {
    Objects.requireNonNull(predicate)
    var removed = false
    val each = this.iterator()
    while (each.hasNext()) {
        if (predicate(each.next())) {
            each.remove()
            removed = true
        }
    }
    return removed
}

/**
 * 判断集合内是否仅有一个元素
 */
fun <T> Collection<T>?.isSingle(): Boolean {
    return this != null && this.size == 1
}

/**
 * 判断集合内是否有多个元素
 */
fun <T> Collection<T>?.isMultiple(): Boolean {
    return this != null && this.size > 1
}

/**
 * 取集合内第二个元素
 */
fun <T> List<T>.second(): T {
    return this[1]
}

/**
 * 取集合内第三个元素
 */
fun <T> List<T>.third(): T {
    return this[2]
}