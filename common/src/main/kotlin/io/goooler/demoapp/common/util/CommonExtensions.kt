@file:Suppress("unused")
@file:JvmName("CommonExtensionUtil")

package io.goooler.demoapp.common.util

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.webkit.URLUtil
import androidx.activity.ComponentActivity
import androidx.annotation.AnyThread
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.annotation.DrawableRes
import androidx.annotation.MainThread
import androidx.annotation.Px
import androidx.annotation.StringRes
import androidx.annotation.UiThread
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.AdaptScreenUtils
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.ResourceUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.TimeUtils
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import dagger.hilt.android.internal.managers.ViewComponentManager
import io.goooler.demoapp.base.core.BaseActivity
import io.goooler.demoapp.base.core.BaseApplication
import io.goooler.demoapp.base.core.BaseFragment
import io.goooler.demoapp.base.core.BaseViewModel
import io.goooler.demoapp.base.util.ToastUtil
import io.goooler.demoapp.common.BuildConfig
import io.goooler.demoapp.common.network.HttpResponse
import io.goooler.demoapp.common.type.SpKeys
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal
import java.util.Calendar
import java.util.Date
import kotlin.math.absoluteValue

typealias DimensionUtil = SizeUtils
typealias SpHelper = SPUtils

val isDebug: Boolean = BuildConfig.DEBUG

inline var isFirstRun: Boolean
  get() = SpHelper.getInstance().getBoolean(SpKeys.FirstRun.key, true)
  set(value) = SpHelper.getInstance().put(SpKeys.FirstRun.key, value)

inline fun debugRun(debug: () -> Unit) {
  if (isDebug) {
    debug()
  }
}

suspend inline fun <reified T : Any> SpKeys.getFromDataStore(name: String = "Demo"): Flow<T?> =
  DataStoreHelper.getInstance(name).get(key)

suspend inline fun <reified T : Any> T.putIntoDataStore(key: SpKeys, prefName: String = "Demo") {
  DataStoreHelper.getInstance(prefName).put(key.key, this)
}

fun Throwable.toast() {
  toString().showToast()
}

@AnyThread
fun @receiver:StringRes Int.showToast() {
  ToastUtil.show(BaseApplication.app, this)
}

@AnyThread
fun String.showToast() {
  ToastUtil.show(BaseApplication.app, this)
}

fun HttpResponse<*>.checkStatusAndEntry() = status && entry != null

fun HttpResponse<*>.checkStatusAndEntryWithToast(): Boolean {
  return checkStatusAndEntry().also {
    if (it.not()) {
      message?.showToast()
    }
  }
}

@UiThread
fun SmartRefreshLayout.finishRefreshAndLoadMore() {
  finishRefresh()
  finishLoadMore()
}

@UiThread
fun SmartRefreshLayout.disableRefreshAndLoadMore() {
  setEnableRefresh(false)
  setEnableLoadMore(false)
}

// ---------------------Convert-------------------------------//

/**
 * 拼上图片前缀
 */
fun String.toLoadUrl(): String {
  return if (URLUtil.isNetworkUrl(this)) this else BuildConfig.CDN_PREFIX + this
}

fun Long.toDateString(pattern: String): String = TimeUtils.millis2String(this, pattern)

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
    isSameYear.not() -> toDateString("yyyy-MM-dd HH:mm")
    isYesterday -> toDateString("昨天 HH:mm")
    t < oneMinute -> "刚刚"
    t < oneHour -> (t / oneMinute).toString() + "分钟前"
    t < oneDay -> (t / oneHour).toString() + "小时前"
    isSameYear -> toDateString("MM-dd HH:mm")
    else -> toDateString("yyyy-MM-dd HH:mm")
  }
}

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
    e.printStackTrace()
    moneyF.toString()
  }
}

// ---------------------Rx-------------------------------//

fun <T> Single<T>.observeOnMainThread(): Single<T> = observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.observeOnMainThread(): Observable<T> =
  observeOn(AndroidSchedulers.mainThread())

// ---------------------Res-------------------------------//

fun @receiver:DrawableRes Int.getDrawable(): Drawable? {
  return try {
    ResourceUtils.getDrawable(this)
  } catch (e: Exception) {
    e.printStackTrace()
    null
  }
}

@ColorInt
fun @receiver:ColorRes Int.getColor(): Int {
  return try {
    ColorUtils.getColor(this)
  } catch (e: Exception) {
    e.printStackTrace()
    -1
  }
}

fun @receiver:StringRes Int.getString(): String? {
  return try {
    StringUtils.getString(this)
  } catch (e: Exception) {
    e.printStackTrace()
    null
  }
}

fun @receiver:StringRes Int.formatString(vararg args: Any): String =
  String.format(getString().orEmpty(), args)

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

fun Bitmap.toDrawable(): Drawable = ImageUtils.bitmap2Drawable(this)

fun Drawable.toBitmap(): Bitmap = ImageUtils.drawable2Bitmap(this)

// ---------------------Fragment-------------------------------//

@MainThread
inline fun <reified T : BaseViewModel> BaseFragment.getViewModel(): Lazy<T> =
  lazy(LazyThreadSafetyMode.NONE) {
    ViewModelProvider(this).get(T::class.java).apply {
      lifecycle.addObserver(this)
    }
  }

inline fun <reified T : ViewDataBinding> Fragment.inflate(crossinline transform: (T) -> Unit = {}): Lazy<T> =
  lazy(LazyThreadSafetyMode.NONE) {
    layoutInflater.inflateBinding<T>().also {
      it.lifecycleOwner = viewLifecycleOwner
      transform(it)
    }
  }

// ---------------------Activity-------------------------------//

@MainThread
inline fun <reified T : BaseViewModel> BaseActivity.getViewModel(): Lazy<T> =
  lazy(LazyThreadSafetyMode.NONE) {
    ViewModelProvider(this).get(T::class.java).apply {
      lifecycle.addObserver(this)
    }
  }

inline fun <reified T : ViewDataBinding> ComponentActivity.inflate(crossinline transform: (T) -> Unit = {}): Lazy<T> =
  lazy(LazyThreadSafetyMode.NONE) {
    layoutInflater.inflateBinding<T>().also {
      setContentView(it.root)
      it.lifecycleOwner = this
      transform(it)
    }
  }

inline fun <reified T : ViewBinding> LayoutInflater.inflateBinding(): T {
  val method = T::class.java.getMethod("inflate", LayoutInflater::class.java)
  return method.invoke(null, this) as T
}

val View.attachedFragment: Fragment?
  get() = try {
    FragmentManager.findFragment(this)
  } catch (_: Exception) {
    null
  }

val View.lifecycle: Lifecycle?
  get() {
    val baseContext = when (context) {
      is ViewComponentManager.FragmentContextWrapper ->
        (context as ViewComponentManager.FragmentContextWrapper).baseContext
      else -> context
    }
    return attachedFragment?.lifecycle ?: (baseContext as? FragmentActivity)?.lifecycle
  }

val View.lifecycleScope: LifecycleCoroutineScope? get() = lifecycle?.coroutineScope
