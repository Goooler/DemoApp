package io.goooler.demoapp.base.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * 时间转换工具简单封装
 * 更新：改用了线程安全的 java 8 自带的时间类
 */

object DateUtil {
    private const val MILLISECONDS = 1000
    private const val SECONDS_TIMESTAMP_LENGTH = 10

    val dateFormats = arrayOf(
        "yyyyMMddHHmmss",
        "yyyy.MM.dd HH:mm:ss",
        "yyyy-MM-dd HH:mm:ss",
        "HH.mm"
    )

    /**
     * 时间戳转日期字符串
     *
     * @param timestamp  注意10位的时间戳要补足成13位
     * @param dateFormat 日期的字符串格式
     * @return 转换后的字符串日期
     */
    fun timestampToDateString(timestamp: Long, dateFormat: String = dateFormats[2]): String {
        val ts = if (timestamp.toString().length == SECONDS_TIMESTAMP_LENGTH) {
            timestamp * MILLISECONDS
        } else {
            timestamp
        }
        return DateTimeFormatter.ofPattern(dateFormat).format(
            LocalDateTime.ofInstant(
                Instant.ofEpochMilli(ts), ZoneId.systemDefault()
            )
        )
    }

    /**
     * 日期字符串转时间戳
     *
     * @param dateString 字符串格式日期
     * @param dateFormat 日期的字符串格式
     * @return 转换后的时间戳
     */
    fun dateStringToTimestamp(dateString: String, dateFormat: String = dateFormats[0]): Long {
        return LocalDateTime.from(
            LocalDateTime.parse(
                dateString,
                DateTimeFormatter.ofPattern(dateFormat)
            )
        ).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }
}

fun Long.toDateString(pattern: String): String {
    return DateUtil.timestampToDateString(this, pattern)
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