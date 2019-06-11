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

    public static boolean isNotEmpty(String string) {
        return isEmpty(string);
    }

    public static boolean isNotEmpty(List list) {
        return !isEmpty(list);
    }

    /**
     * 调用不同类型的判断方法分类判断
     *
     * @param o 传入任意类型
     * @return 是否为空
     */
    public static boolean isEmpty(Object o) {
        boolean empty = true;
        if (o instanceof String) {
            empty = isEmpty((String) o);
        } else if (o instanceof List) {
            empty = isEmpty((List) o);
        }
        return empty;
    }

    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }
}

