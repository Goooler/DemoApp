@file:Suppress("TooGenericExceptionCaught", "MagicNumber", "PrintStackTrace")

package io.goooler.demoapp.obsolete.util

import com.blankj.utilcode.util.TimeUtils
import io.goooler.demoapp.common.network.HttpResponse
import io.goooler.demoapp.obsolete.network.exception.ResponseException
import io.goooler.demoapp.obsolete.network.exception.toResponseException
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.math.BigDecimal
import java.util.Calendar
import java.util.Date
import kotlin.math.absoluteValue

@Throws(ResponseException::class)
fun <T : Any> HttpResponse<T>.checkCodeWithException(): T? {
  if (code != 200) throw (message ?: code.toString()).toResponseException()
  return entry
}

// ---------------------Rx-------------------------------//

fun <T : Any> Single<T>.subscribeOnIoThread(): Single<T> = subscribeOn(Schedulers.io())

fun <T : Any> Single<T>.observeOnMainThread(): Single<T> = observeOn(AndroidSchedulers.mainThread())

fun <T : Any> Single<T>.subscribeAndObserve(): Single<T> =
  subscribeOnIoThread().observeOnMainThread()

fun <T : Any> Observable<T>.subscribeOnIoThread(): Observable<T> = subscribeOn(Schedulers.io())

fun <T : Any> Observable<T>.observeOnMainThread(): Observable<T> =
  observeOn(AndroidSchedulers.mainThread())

fun <T : Any> Observable<T>.subscribeAndObserve(): Observable<T> =
  subscribeOnIoThread().observeOnMainThread()

// ---------------------Convert-------------------------------//

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
@Suppress("NestedBlockDepth")
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
