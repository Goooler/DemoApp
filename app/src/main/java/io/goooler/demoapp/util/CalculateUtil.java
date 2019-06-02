package io.goooler.demoapp.util;

/**
 * 简单的计算工具封装，作判断
 */
public class CalculateUtil {

    /**
     * 双开区间
     */
    public static boolean isIn(double start, double end, double value) {
        return value > start && value < end;
    }

    public static boolean isNotIn(double start, double end, double value) {
        return !isIn(start, end, value);
    }

    /**
     * 双闭区间
     */
    public static boolean isBetween(double start, double end, double value) {
        return value >= start && value <= end;
    }

    public static boolean isNotBetween(double start, double end, double value) {
        return !isIn(start, end, value);
    }
}
