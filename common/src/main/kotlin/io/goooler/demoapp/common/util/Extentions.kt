@file:Suppress("unused")

package io.goooler.demoapp.common.util

import android.graphics.drawable.Drawable
import android.webkit.URLUtil
import androidx.activity.ComponentActivity
import androidx.annotation.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.*
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import io.goooler.demoapp.base.core.BaseViewModel
import io.goooler.demoapp.base.network.HttpResponse
import io.goooler.demoapp.common.BuildConfig
import io.goooler.demoapp.common.type.SpKeys
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import java.util.*

typealias DimensionUtil = SizeUtils
typealias SpHelper = SPUtils

val isDebug: Boolean = BuildConfig.DEBUG

var isFirstRun: Boolean
    get() = SpHelper.getInstance().getBoolean(SpKeys.FirstRun.key, true)
    set(value) = SpHelper.getInstance().put(SpKeys.FirstRun.key, value)

inline fun debugRun(debug: () -> Unit) {
    if (isDebug) {
        debug()
    }
}

/**
 * 拼上图片前缀
 */
fun String.toLoadUrl(): String {
    return if (URLUtil.isNetworkUrl(this)) this else BuildConfig.CDN_PREFIX + this
}

fun SmartRefreshLayout.finishRefreshAndLoadMore() {
    finishRefresh()
    finishLoadMore()
}

fun SmartRefreshLayout.disableRefreshAndLoadMore() {
    setEnableRefresh(false)
    setEnableLoadMore(false)
}

fun Long.toDateString(pattern: String): String {
    return TimeUtils.millis2String(this, pattern)
}

fun Long.easyTime(): String {
    val now = System.currentTimeMillis()
    val t = now - this
    if (t < 0) {
        // 未来
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

@Px
fun @receiver:Dimension(unit = Dimension.SP) Float.sp2px(): Int = SizeUtils.sp2px(this)

@Px
fun @receiver:Dimension(unit = Dimension.DP) Float.dp2px(): Int = SizeUtils.dp2px(this)

@Px
fun Float.pt2px(): Int = AdaptScreenUtils.pt2Px(this)

@Dimension(unit = Dimension.SP)
fun @receiver:Dimension Int.px2sp(): Int = SizeUtils.px2sp(this.toFloat())

@Dimension(unit = Dimension.DP)
fun @receiver:Dimension Int.px2dp(): Int = SizeUtils.px2dp(this.toFloat())

fun @receiver:Dimension Int.px2pt(): Int = AdaptScreenUtils.px2Pt(this.toFloat())

//---------------------Rx-------------------------------//

fun <T> Single<T>.observeOnMainThread(): Single<T> {
    return observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.observeOnMainThread(): Observable<T> {
    return observeOn(AndroidSchedulers.mainThread())
}

//---------------------Res-------------------------------//

fun @receiver:DrawableRes Int.getDrawable(): Drawable? = ResourceUtils.getDrawable(this)

@ColorInt
fun @receiver:ColorRes Int.getColor(): Int = ColorUtils.getColor(this)

fun @receiver:StringRes Int.getString(): String = StringUtils.getString(this)

fun @receiver:StringRes Int.formatString(vararg args: Any): String =
    String.format(getString(), args)

//---------------------Fragment-------------------------------//

@MainThread
inline fun <reified T : BaseViewModel> Fragment.getViewModel(): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this).get(T::class.java).apply {
            lifecycle.addObserver(this)
        }
    }
}

@MainThread
inline fun <reified T : BaseViewModel> Fragment.getViewModelOfActivity(): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(requireActivity()).get(T::class.java).apply {
            lifecycle.addObserver(this)
        }
    }
}

//---------------------Activity-------------------------------//

@MainThread
inline fun <reified T : BaseViewModel> ComponentActivity.getViewModel(): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this).get(T::class.java).apply {
            lifecycle.addObserver(this)
        }
    }
}

//---------------------ViewModel-------------------------------//

fun BaseViewModel.checkStatusAndEntry(response: HttpResponse<*>) =
    response.status && response.entry != null

fun BaseViewModel.checkStatusAndEntryWithToast(response: HttpResponse<*>): Boolean {
    return checkStatusAndEntry(response).also {
        if (!it) {
            response.message?.showToast()
        }
    }
}

fun BaseViewModel.toastThrowable(throwable: Throwable) {
    throwable.toString().showToast()
}