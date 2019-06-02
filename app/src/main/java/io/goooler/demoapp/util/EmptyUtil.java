package io.goooler.demoapp.util;

import java.util.List;

/**
 * 判空工具
 */
public class EmptyUtil {


    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }

    public static boolean isEmpty(List list) {
        return list == null || list.size() == 0;
    }
}
