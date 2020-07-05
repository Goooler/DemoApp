package io.goooler.demoapp.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

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