@file:Suppress("unused")

package io.goooler.demoapp.base.util

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.text.Html
import android.text.Spanned
import android.webkit.URLUtil
import androidx.annotation.ColorInt
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.MutableLiveData
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

typealias ObservableString = ObservableField<String>

typealias ObservableList<T> = ObservableField<List<T>>

typealias ParamMap = HashMap<String, Any>

//---------------------Any-------------------------------//

val currentTimeMillis: Long get() = System.currentTimeMillis()

val currentThreadName: String get() = Thread.currentThread().name

val isMainThread: Boolean get() = Looper.getMainLooper().thread === Thread.currentThread()

fun <T> unsafeLazy(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)

//---------------------CharSequence-------------------------------//

fun String.fromHtml(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(this)
    }
}

fun CharSequence?.isNotNullOrEmpty(): Boolean = !isNullOrEmpty()

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

fun String?.isNetworkUrl(): Boolean = URLUtil.isNetworkUrl(this)

fun String?.isUri(): Boolean = URLUtil.isValidUrl(this)

/**
 * 验证手机号格式是否正确
 */
fun String.isValidPhoneFormat(): Boolean = startsWith("1") && length == 11

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

fun Int?.orZero(): Int = this ?: 0

fun Boolean?.orTrue(): Boolean = this ?: true

fun Boolean?.orFalse(): Boolean = this ?: false

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

fun <T> Collection<T>?.isNotNullOrEmpty(): Boolean = !isNullOrEmpty()

/**
 * 判断集合内是否仅有一个元素
 */
fun <T> Collection<T>?.isSingle(): Boolean = this != null && size == 1

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

fun paramMapOf(vararg pairs: Pair<String, Any>): HashMap<String, Any> =
    HashMap<String, Any>(pairs.size).apply { putAll(pairs) }

//---------------------Coroutine-------------------------------//

fun <T> CoroutineScope.defaultAsync(
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> T
) = async(SupervisorJob(), start, block)

suspend fun <T> withIoContext(block: suspend CoroutineScope.() -> T) =
    withContext(Dispatchers.IO, block)

suspend fun <T> withDefaultContext(block: suspend CoroutineScope.() -> T) =
    withContext(Dispatchers.Default, block)

//---------------------File-------------------------------//

fun File.notExists(): Boolean = !this.exists()

//---------------------Fragment-------------------------------//

fun <T : Fragment> T.putArguments(bundle: Bundle): T {
    arguments = bundle
    return this
}

/**
 * @param containerViewId   容器 id
 * @param fragment          要添加的 fragment
 * @param isAddToBackStack  将要添加的 fragment 是否要添加到返回栈，默认不添加
 * @param tag               fragment 的 tag
 */
fun FragmentManager.addFragment(
    @IdRes containerViewId: Int,
    fragment: Fragment,
    isAddToBackStack: Boolean = false,
    tag: String? = null
) {
    if (fragment.isAdded) return
    commit {
        if (isAddToBackStack) addToBackStack(tag)
        add(containerViewId, fragment, tag)
    }
}

/**
 * @param containerViewId   容器 id
 * @param fragment          要替换的 fragment
 * @param isAddToBackStack  将要替换的 fragment 是否要添加到返回栈，默认添加
 * @param tag               fragment 的 tag
 */
fun FragmentManager.replaceFragment(
    @IdRes containerViewId: Int,
    fragment: Fragment,
    isAddToBackStack: Boolean = true,
    tag: String? = null
) {
    if (fragment.isAdded) return
    commit {
        if (isAddToBackStack) addToBackStack(tag)
        replace(containerViewId, fragment, tag)
    }
}

//---------------------Activity-------------------------------//

inline fun <reified T : ViewDataBinding> Activity.binding(@LayoutRes resId: Int): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE) { DataBindingUtil.setContentView(this, resId) }

//---------------------Other-------------------------------//

/**
 * 条件为真时执行
 */
inline fun Boolean.trueRun(block: () -> Unit) {
    if (this) {
        block()
    }
}

/**
 * 条件为假时执行
 */
inline fun Boolean.falseRun(block: () -> Unit) {
    if (!this) {
        block()
    }
}