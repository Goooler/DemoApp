package io.goooler.demoapp.base.util

import io.goooler.demoapp.base.core.BaseApplication

object TraceManager {

    fun reportUncaughtException(log: String) {
        ToastUtil.showToastInAnyThread(BaseApplication.context, log)
    }
}