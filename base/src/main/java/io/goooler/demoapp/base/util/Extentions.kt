@file:Suppress("unused")

package io.goooler.demoapp.base.util

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Looper
import android.text.Html
import android.text.Spanned
import android.view.View
import android.webkit.URLUtil
import androidx.annotation.ColorInt
import androidx.lifecycle.MutableLiveData
import io.goooler.demoapp.base.BuildConfig
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.*
import java.io.File
import java.math.BigDecimal
import kotlin.math.absoluteValue

//---------------------Types-------------------------------//

typealias MutableBooleanLiveData = MutableLiveData<Boolean>

typealias MutableIntLiveData = MutableLiveData<Int>

typealias MutableLongLiveData = MutableLiveData<Long>

typealias MutableDoubleLiveData = MutableLiveData<Double>

typealias MutableFloatLiveData = MutableLiveData<Float>

typealias MutableStringLiveData = MutableLiveData<String>

typealias MutableListLiveData<T> = MutableLiveData<List<T>>

//---------------------Any-------------------------------//

val isDebug: Boolean = BuildConfig.DEBUG

const val versionCode: Int = BuildConfig.VERSION_CODE

val currentTimeMillis: Long get() = System.currentTimeMillis()

val currentThreadName: String get() = Thread.currentThread().name

val isMainThread: Boolean get() = Looper.getMainLooper().thread === Thread.currentThread()

fun <T> unsafeLazy(initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)

//---------------------CharSequence-------------------------------//

fun String.fromHtml(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(this)
    }
}

fun CharSequence?.isNotNullOrEmpty(): Boolean {
    return !isNullOrEmpty()
}

/**
 * subString 防越界处理
 */
fun String.safeSubstring(startIndex: Int, endIndex: Int): String {
    val begin = if (startIndex < 0) 0 else startIndex
    val end = if (endIndex > length) length else endIndex
    return substring(begin, end)
}

fun String?.safeToBoolean(default: Boolean = false): Boolean {
    if (this == null) return default
    return try {
        toBoolean()
    } catch (e: Throwable) {
        default
    }
}

fun String?.safeToInt(default: Int = 0): Int {
    if (this == null) return default
    return try {
        toInt()
    } catch (e: Throwable) {
        default
    }
}

fun String?.safeToLong(default: Long = 0L): Long {
    if (this == null) return default
    return try {
        toLong()
    } catch (e: Throwable) {
        default
    }
}

fun String?.safeToFloat(default: Float = 0f): Float {
    if (this == null) return default
    return try {
        toFloat()
    } catch (e: Throwable) {
        default
    }
}

fun String?.safeToDouble(default: Double = 0.0): Double {
    if (this == null) return default
    return try {
        toDouble()
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

/**
 * 验证是否属于正确的 url 格式
 */
fun String.isNetworkUrl(): Boolean {
    return URLUtil.isNetworkUrl(this)
}

/**
 * 验证手机号格式是否正确
 */
fun String.isValidPhoneFormat(): Boolean {
    return startsWith("1") && length == 11
}

/**
 * 隐藏手机号
 */
fun String.hidePhone(): String {
    return replace(Regex("(\\d{3})\\d{4}(\\d{4})"), "$1****$2")
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
        toDouble()
    } else {
        // 分转为元
        toDouble() / 100
    }
    return try {
        when {
            trans2W && moneyF / 10000 > 0 -> {
                BigDecimal.valueOf(moneyF / 10000)
                    .setScale(1, BigDecimal.ROUND_DOWN)
                    .stripTrailingZeros().toPlainString() + "W"
            }

            else ->
                BigDecimal.valueOf(moneyF)
                    .setScale(scale, BigDecimal.ROUND_DOWN)
                    .stripTrailingZeros().toPlainString()
                    .let {
                        if (it.toDouble().absoluteValue < 0.000001) {
                            "0"
                        } else {
                            it
                        }
                    }
        }
    } catch (e: Exception) {
        moneyF.toString()
    }
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

fun Number.isNotZero(): Boolean = !isZero()

fun Boolean?.orTrue(): Boolean = this ?: true

fun Boolean?.orFalse(): Boolean = this ?: false

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
fun View.setBgShapeGradual(
    shapeType: Int = GradientDrawable.RECTANGLE,
    @ColorInt gradualColors: IntArray? = null,
    angle: Int = -1,
    @ColorInt solidColor: Int? = null,
    @ColorInt strokeColor: Int = Color.TRANSPARENT,
    stroke: Float = 0f,
    radius: Float = 0f
) {
    background = GradientDrawable().apply {
        shape = shapeType
        useLevel = false
        gradientType = GradientDrawable.LINEAR_GRADIENT
        orientation = when (angle) {
            45 -> GradientDrawable.Orientation.BL_TR
            90 -> GradientDrawable.Orientation.BOTTOM_TOP
            135 -> GradientDrawable.Orientation.BR_TL
            180 -> GradientDrawable.Orientation.RIGHT_LEFT
            225 -> GradientDrawable.Orientation.TR_BL
            270 -> GradientDrawable.Orientation.TOP_BOTTOM
            315 -> GradientDrawable.Orientation.TL_BR
            else -> GradientDrawable.Orientation.LEFT_RIGHT
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
fun View.setBgShapeCorners(
    @ColorInt solidColor: Int = Color.WHITE,
    topLeft: Float = 0f,
    topRight: Float = 0f,
    bottomLeft: Float = 0f,
    bottomRight: Float = 0f
) {
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

//---------------------Collections-------------------------------//

inline fun <E> MutableCollection<E>.removeIfMatch(predicate: (e: E) -> Boolean): Boolean {
    var removed = false
    val each = iterator()
    while (each.hasNext()) {
        if (predicate(each.next())) {
            each.remove()
            removed = true
        }
    }
    return removed
}

fun <E> MutableList<E>.remove() {
    removeAt(0)
}

fun <T> Collection<T>?.isNotNullOrEmpty(): Boolean {
    return !isNullOrEmpty()
}

/**
 * 判断集合内是否仅有一个元素
 */
fun <T> Collection<T>?.isSingle(): Boolean {
    return this != null && size == 1
}

/**
 * 判断集合内是否有多个元素
 * @param minSize 最小为 2
 */
fun <T> Collection<T>?.isMultiple(minSize: Int = 2): Boolean {
    val min = if (minSize < 2) 2 else minSize
    return this != null && size >= min
}

/**
 * 取集合内第二个元素
 */
fun <T> List<T>.secondOrNull(): T? {
    return if (size < 2) null else this[1]
}

/**
 * 取集合内第三个元素
 */
fun <T> List<T>.thirdOrNull(): T? {
    return if (size < 3) null else this[2]
}

//---------------------Coroutine-------------------------------//

fun <T> CoroutineScope.defaultAsync(
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> T
) = async(SupervisorJob(), start, block)

suspend fun <T> withIoContext(block: suspend CoroutineScope.() -> T) =
    withContext(Dispatchers.IO, block)

suspend fun <T> withDefaultContext(block: suspend CoroutineScope.() -> T) =
    withContext(Dispatchers.Default, block)

//---------------------Rx-------------------------------//

fun <T> Single<T>.subscribeAndObserve(): Single<T> {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

//---------------------File-------------------------------//

fun File.notExist(): Boolean = !this.exists()

//---------------------HigherOrderFunc-------------------------------//

/**
 * 条件为真时执行
 */
inline fun Boolean.trueRun(whenTrue: () -> Unit) {
    if (this) {
        whenTrue()
    }
}

/**
 * 条件为假时执行
 */
inline fun Boolean.falseRun(whenFalse: () -> Unit) {
    if (!this) {
        whenFalse()
    }
}

inline fun debugRun(debug: () -> Unit) {
    if (BuildConfig.DEBUG) {
        debug()
    }
}