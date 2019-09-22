package io.goooler.demoapp.util

import android.annotation.SuppressLint
import io.goooler.demoapp.R
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * 时间转换工具简单封装
 * 更新：改用了线程安全的 java 8 自带的时间类
 */

object DateUtil {
    const val MILLISECONDS = 1000
    const val TIMESTAMP_LENGTH = 10

    private val currentTimeMillis: Long
        get() = System.currentTimeMillis() / MILLISECONDS

    /**
     * @return 字符串格式如 yyyy.MM.dd HH:mm
     */
    fun timestampToDateString(timestamp: Long?): String {
        return timestampToDateString(timestamp!!, ResUtil.getStringArray(R.array.date_format)[0])
    }

    /**
     * 时间戳转日期字符串
     *
     * @param timestamp  注意10位的时间戳要补足成13位
     * @param dateFormat 日期的字符串格式
     * @return 转换后的字符串日期
     */
    @SuppressLint("NewApi")
    fun timestampToDateString(timestamp: Long, dateFormat: String): String {
        val ts = if (timestamp.toString().length == TIMESTAMP_LENGTH) {
            timestamp * MILLISECONDS
        } else {
            timestamp
        }
        return DateTimeFormatter.ofPattern(dateFormat).format(LocalDateTime.ofInstant(
                Instant.ofEpochMilli(ts), ZoneId.systemDefault()))
    }

    /**
     * 时间戳转长整数，
     *
     * @return 长整数格式例如 yyyyMMddHHmmss
     */
    fun timestampToDateLong(timestamp: Long?): Long {
        return java.lang.Long.valueOf(timestampToDateString(timestamp!!, ResUtil.getStringArray(R.array.date_format)[1]))
    }

    /**
     * 完整的 yyyyMMddHHmmss 14 位上截取需要的单位，例如年、月、日等
     *
     * @param beginIndex 截取起始位置
     * @param endIndex   截取终止位置
     * @return 例如 2019（年）、06（月）、（07）日
     */
    private fun timestampToDateUnit(timestamp: Long?, beginIndex: Int, endIndex: Int): Long {
        return java.lang.Long.valueOf(timestampToDateLong(timestamp).toString().substring(beginIndex, endIndex))
    }

    /**
     * 时间戳转小时，
     *
     * @return 整数格式例如 HH
     */
    @JvmOverloads
    fun timestampToHourInt(timestamp: Long? = currentTimeMillis): Int {
        return timestampToDateUnit(timestamp, 8, 10).toInt()
    }

    /**
     * 日期字符串转时间戳
     *
     * @param dateString 字符串格式日期
     * @param dateFormat 日期的字符串格式
     * @return 转换后的时间戳
     */
    @SuppressLint("NewApi")
    fun dateStringToTimestamp(dateString: String, dateFormat: String): Long {
        return LocalDateTime.from(LocalDateTime.parse(dateString,
                DateTimeFormatter.ofPattern(dateFormat))).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    fun timestampToDateFloat(timestamp: Long?): Float {
        return java.lang.Float.valueOf(timestampToDateString(timestamp!!, ResUtil.getStringArray(R.array.date_format)[2]))
    }
}
/**
 * @return 当前的小时
 */
