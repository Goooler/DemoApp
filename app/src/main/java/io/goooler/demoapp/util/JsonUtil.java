package io.goooler.demoapp.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

/**
 * 对 fastJson 的简单封装，以便统一异常处理等操作
 */

public class JsonUtil {

    public static <T> T parse(String jsonString, Class<T> clazz) {
        T t = null;
        try {
            t = JSONObject.parseObject(jsonString, clazz);
        } catch (JSONException e) {
        }
        return t;
    }
}
