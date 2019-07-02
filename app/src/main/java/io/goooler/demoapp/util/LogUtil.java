package io.goooler.demoapp.util;

import android.util.Log;

import io.goooler.demoapp.base.BaseApplication;
import io.goooler.demoapp.model.Constants;

/**
 * Log 工具的简单封装，可自由控制全局 log 输出
 * TODO: 这里的日志输出与否的值可记在 sp 中方便更改，后期可加入后门控制
 */

public class LogUtil {

    private static boolean showLog = BaseApplication.isApkDebugable();
    private static final String DEFAULT_LOG_TAG = "defaultLogTag";

    public static void d(String debugInfo) {
        log(DEFAULT_LOG_TAG, debugInfo);
    }

    public static void d(String tag, String debugInfo) {
        log(tag, debugInfo);
    }

    private static void log(String tag, String debugInfo) {
        if (showLog) {
            if (EmptyUtil.isEmpty(debugInfo)) {
                Log.d(tag, Constants.NULL_STRING);
            } else {
                Log.d(tag, debugInfo);
            }
        }
    }
}
