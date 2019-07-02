package io.goooler.demoapp.util;

import android.annotation.SuppressLint;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import io.goooler.demoapp.R;

/**
 * 时间转换工具简单封装
 * 更新：改用了线程安全的 java 8 自带的时间类
 */

public class DateUtil {
    public static final int MILLISECONDS = 1000;
    public static final int TIMESTAMPE_LENGTH = 10;

    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis() / MILLISECONDS;
    }

    /**
     * @return 字符串格式如 yyyy.MM.dd HH:mm
     */
    public static String timestampToDateString(Long timestamp) {
        return timestampToDateString(timestamp, ResUtil.getStringArray(R.array.date_format)[0]);
    }

    /**
     * 时间戳转日期字符串
     *
     * @param timestamp  注意10位的时间戳要补足成13位
     * @param dateFormat 日期的字符串格式
     * @return 转换后的字符串日期
     */
    @SuppressLint("NewApi")
    public static String timestampToDateString(Long timestamp, String dateFormat) {
        if (timestamp.toString().length() == TIMESTAMPE_LENGTH) {
            timestamp = timestamp * MILLISECONDS;
        }
        return DateTimeFormatter.ofPattern(dateFormat).format(LocalDateTime.ofInstant(
                Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()));
    }

    /**
     * 时间戳转长整数，
     *
     * @return 长整数格式例如 yyyyMMddHHmmss
     */
    public static long timestampToDateLong(Long timestamp) {
        return Long.valueOf(timestampToDateString(timestamp, ResUtil.getStringArray(R.array.date_format)[1]));
    }

    /**
     * 完整的 yyyyMMddHHmmss 14 位上截取需要的单位，例如年、月、日等
     *
     * @param beginIndex 截取起始位置
     * @param endIndex   截取终止位置
     * @return 例如 2019（年）、06（月）、（07）日
     */
    private static long timestampToDateUnit(Long timestamp, int beginIndex, int endIndex) {
        return Long.valueOf(String.valueOf(timestampToDateLong(timestamp)).
                substring(beginIndex, endIndex));
    }

    /**
     * 时间戳转小时，
     *
     * @return 整数格式例如 HH
     */
    public static int timestampToHourInt(Long timestamp) {
        return (int) timestampToDateUnit(timestamp, 8, 10);
    }

    /**
     * @return 当前的小时
     */
    public static int timestampToHourInt() {
        return timestampToHourInt(getCurrentTimeMillis());
    }

    /**
     * 日期字符串转时间戳
     *
     * @param dateString 字符串格式日期
     * @param dateFormat 日期的字符串格式
     * @return 转换后的时间戳
     */
    @SuppressLint("NewApi")
    public static long dateStringToTimestamp(String dateString, String dateFormat) {
        return LocalDateTime.from(LocalDateTime.parse(dateString,
                DateTimeFormatter.ofPattern(dateFormat))).
                atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static float timestampToDateFloat(Long timestamp) {
        return Float.valueOf(timestampToDateString(timestamp, ResUtil.getStringArray(R.array.date_format)[2]));
    }
}
