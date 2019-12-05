package io.goooler.demoapp.util

import android.util.Log
import io.goooler.demoapp.BuildConfig

/**
 * Log 工具的简单封装，可自由控制全局 log 输出
 */

object LogUtil {

    private const val DEFAULT_LOG_TAG = "goooler"

    @JvmStatic
    fun d(debugInfo: Any?) {
        debugInfo?.let {
            log(DEFAULT_LOG_TAG, it.toString())
        }
    }

    @JvmStatic
    fun d(tag: Any, debugInfo: Any?) {
        debugInfo?.let {
            log(tag.toString(), it.toString())
        }
    }

    @JvmStatic
    private fun log(tag: String, debugInfo: String) {
        if (BuildConfig.DEBUG) {
            if (debugInfo.isNotEmpty()) {
                Log.d(tag, debugInfo)
            }
        }
    }
}
