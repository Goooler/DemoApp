package io.goooler.demoapp.base.util

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.view.View
import android.webkit.URLUtil
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.annotation.StringRes
import io.goooler.demoapp.base.BuildConfig
import io.goooler.demoapp.base.core.BaseApplication
import io.goooler.demoapp.base.core.BaseObjectBoxEntity
import io.goooler.demoapp.base.model.Constants.IMAGE_URL_PREFIX
import io.goooler.demoapp.base.model.Constants.PHONE_FIRST_CHAR
import io.goooler.demoapp.base.model.Constants.PHONE_LENGTH
import io.goooler.demoapp.base.util.device.DimensionUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.*
import java.math.BigDecimal
import java.util.*
import kotlin.math.absoluteValue
import kotlin.reflect.KClass

//---------------------Any-------------------------------//


fun Any?.log() {
    LogUtil.d(this)
}

val isDebug: Boolean
    get() = BuildConfig.DEBUG

val versionCode: Int
    get() = BuildConfig.VERSION_CODE

val currentTimeMillis: Long
    get() = System.currentTimeMillis()

val currentThreadName: String
    get() = Thread.currentThread().name

fun <T> unsafeLazy(initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)


//---------------------CharSequence-------------------------------//


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

/**
 * 获取图片宽高比例，如：/assets/img/2019/07/18/n_1563460410803_3849___size550x769.jpg
 */
fun String.getSizeByLoadUrl(defaultWidth: Int, defaultHeight: Int): List<Int> {
    val sizeList = ArrayList<Int>()
    sizeList.add(defaultWidth)
    sizeList.add(defaultHeight)
    val flag = "size"
    if (!contains(IMAGE_URL_PREFIX) || !contains(flag)) {
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


//---------------------Date-------------------------------//


fun Long.toDateString(pattern: String): String {
    return DateUtil.timestampToDateString(this, pattern)
}

fun Long.easyTime(): String {
    val now = System.currentTimeMillis()
    val t = now - this
    if (t < 0) {// 未来
        return toDateString("yyyy-MM-dd HH:mm")
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
        !isSameYear -> toDateString("yyyy-MM-dd HH:mm")
        isYesterday -> toDateString("昨天 HH:mm")
        t < oneMinute -> "刚刚"
        t < oneHour -> (t / oneMinute).toString() + "分钟前"
        t < oneDay -> (t / oneHour).toString() + "小时前"
        isSameYear -> toDateString("MM-dd HH:mm")
        else -> toDateString("yyyy-MM-dd HH:mm")
    }
}


//---------------------Network-------------------------------//

/**
 * 验证是否属于正确的 url 格式
 */
fun String.isNetworkUrl(): Boolean {
    return URLUtil.isNetworkUrl(this)
}

/**
 * 拼上图片前缀
 */
fun String.toLoadUrl(): String {
    return if (isNetworkUrl()) {
        this
    } else {
        IMAGE_URL_PREFIX + this
    }
}


//---------------------Device-------------------------------//


/**
 * 验证手机号格式是否正确
 */
fun String.isValidPhoneFormat(): Boolean {
    return startsWith(PHONE_FIRST_CHAR) && length == PHONE_LENGTH
}

/**
 * 隐藏手机号
 */
fun String.hidePhone(): String {
    return replace(Regex("(\\d{3})\\d{4}(\\d{4})"), "$1****$2")
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


//---------------------SafeTrans-------------------------------//


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


//---------------------Database-------------------------------//


inline fun <reified T : BaseObjectBoxEntity> T.putIntoBox() {
    ObjectBox.put(this)
}

inline fun <reified T : BaseObjectBoxEntity> Collection<T>.putIntoBox() {
    return ObjectBox.put(this)
}

inline fun <reified T : BaseObjectBoxEntity> KClass<T>.getAllFromBox(): List<T> {
    return ObjectBox.getAll()
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