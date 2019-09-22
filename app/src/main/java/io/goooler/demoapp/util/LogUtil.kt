package io.goooler.demoapp.util

import android.util.Log
import io.goooler.demoapp.BuildConfig
import io.goooler.demoapp.model.Constants

/**
 * Log 工具的简单封装，可自由控制全局 log 输出
 * TODO: 这里的日志输出与否的值可记在 sp 中方便更改，后期可加入后门控制
 */

object LogUtil {

    private val showLog = BuildConfig.DEBUG
    private const val DEFAULT_LOG_TAG = "defaultLogTag"

    fun d(debugInfo: String) {
        log(DEFAULT_LOG_TAG, debugInfo)
    }

    fun d(tag: String, debugInfo: String) {
        log(tag, debugInfo)
    }

    private fun log(tag: String, debugInfo: String?) {
        if (showLog) {
            if (debugInfo.isNullOrEmpty()) {
                Log.d(tag, Constants.NULL_STRING)
            } else {
                Log.d(tag, debugInfo)
            }
        }
    }
}
