package io.goooler.demoapp.common.util

import android.util.Log
import io.goooler.demoapp.common.BuildConfig

object LogUtil {

  private const val DEFAULT_LOG_TAG = "defaultLogTag"

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
