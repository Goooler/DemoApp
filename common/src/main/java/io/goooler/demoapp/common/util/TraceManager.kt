package io.goooler.demoapp.common.util

import io.goooler.demoapp.base.core.BaseApplication
import io.goooler.demoapp.base.util.ToastUtil

object TraceManager {

    fun reportUncaughtException(log: String) {
        ToastUtil.showToastInAnyThread(
            BaseApplication.context,
            log
        )
    }
}