@file:Suppress("unused")

package io.goooler.demoapp.base.util

import android.util.Log
import io.goooler.demoapp.base.BuildConfig

/**
 * Log 工具的简单封装，可自由控制全局 log 输出
 */

object LogUtil {

    private const val DEFAULT_LOG_TAG = "defaultLogTag"

    @JvmStatic
    @JvmOverloads
    fun d(debugInfo: Any?, tag: String = DEFAULT_LOG_TAG) {
        out(tag, debugInfo.toString())
    }

    private fun out(tag: String, debugInfo: String) {
        if (BuildConfig.DEBUG && debugInfo.isNotEmpty()) {
            Log.d(tag, debugInfo)
        }
    }
}

fun Any?.log() {
    LogUtil.d(this)
}