package io.goooler.demoapp.util

import android.webkit.URLUtil
import io.goooler.demoapp.model.Constants.IMAGE_URL_PREFIX
import io.goooler.demoapp.model.Constants.PHONE_FIRST_CHAR
import io.goooler.demoapp.model.Constants.PHONE_LENGTH
import java.math.BigDecimal
import java.util.*


/**
 * Created by JokerWan on 2019-05-14.
 * Function:
 */


/**
 * 泛型传入要解析的data类的type
 * 只能适用于简单的对象不能解析集合类泛型（例如：List<MyObj>）
 */
inline fun <reified T> String.fromJson(): T? {
    return JsonUtil.fromJson(this, T::class.java)
}

fun Any.toJson(): String {
    return JsonUtil.toJson(this)
}

/**
 * @param isYuan 默认以分为单位，传入元为单位传 true
 * @param trans2W 是否需要在超过一万时转换为 1.2w 的形式，不需要的话传 false
 * @param scale 保留小数的位数
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
    return when {
        moneyF.toLong() / 10000 > 0 -> {
            if (trans2W) {
                return try {
                    BigDecimal.valueOf(moneyF / 10000)
                            .setScale(1, BigDecimal.ROUND_DOWN)
                            .stripTrailingZeros().toPlainString() + "W"
                } catch (e: Exception) {
                    this.toString()
                }
            } else {
                BigDecimal.valueOf(moneyF)
                        .setScale(scale, BigDecimal.ROUND_DOWN)
                        .stripTrailingZeros().toPlainString()
            }
        }
        else -> {
            BigDecimal.valueOf(moneyF).setScale(scale, BigDecimal.ROUND_DOWN)
                    .stripTrailingZeros().toPlainString().let {
                        return if (it.toDouble() == 0.0) {
                            "0"
                        } else {
                            it
                        }
                    }
        }
    }
}

infix fun Number.plus(that: Number): Double {
    return (BigDecimal(this.toString()) + BigDecimal(that.toString())).toDouble()
}

infix fun Number.minus(that: Number): Double {
    return (BigDecimal(this.toString()) - BigDecimal(that.toString())).toDouble()
}

infix fun Number.times(that: Number): Double {
    return (BigDecimal(this.toString()) * BigDecimal(that.toString())).toDouble()
}

infix fun Number.div(that: Number): Double {
    return (BigDecimal(this.toString()) / BigDecimal(that.toString())).toDouble()
}

fun Long.trans2DateString(pattern: String): String {
    return DateUtil.timestampToDateString(this, pattern)
}

fun String.toLoadUrl(): String {
    return if (URLUtil.isNetworkUrl(this)) {
        this
    } else {
        IMAGE_URL_PREFIX + this
    }
}

fun Long.easyTime(): String {
    val now = System.currentTimeMillis()
    val t = now - this
    if (t < 0) {// 未来
        return this.trans2DateString("yyyy-MM-dd HH:mm")
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
        !isSameYear -> this.trans2DateString("yyyy-MM-dd HH:mm")
        isYesterday -> this.trans2DateString("昨天 HH:mm")
        t < oneMinute -> "刚刚"
        t < oneHour -> (t / oneMinute).toString() + "分钟前"
        t < oneDay -> (t / oneHour).toString() + "小时前"
        isSameYear -> this.trans2DateString("MM-dd HH:mm")
        else -> this.trans2DateString("yyyy-MM-dd HH:mm")
    }
}

fun String.isValidPhoneFormat(): Boolean {
    return this.startsWith(PHONE_FIRST_CHAR) && this.length == PHONE_LENGTH
}

fun String.hidePhone(): String {
    return this.replace(Regex("(\\d{3})\\d{4}(\\d{4})"), "$1****$2")
}