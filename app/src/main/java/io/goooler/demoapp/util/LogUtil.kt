package io.goooler.demoapp.util

import io.goooler.demoapp.BuildConfig
import timber.log.Timber

/**
 * Log 工具的简单封装，可自由控制全局 log 输出
 */

object LogUtil {

    private val showLog = BuildConfig.DEBUG
    private const val DEFAULT_LOG_TAG = "defaultLogTag"

    @JvmStatic
    fun d(debugInfo: String) {
        log(DEFAULT_LOG_TAG, debugInfo)
    }

    @JvmStatic
    fun d(tag: String, debugInfo: String) {
        log(tag, debugInfo)
    }

    private fun log(tag: String, debugInfo: String) {
        if (showLog) {
            if (debugInfo.isNotEmpty()) {
                Timber.tag(tag).d(debugInfo)
            }
        }
    }
}
