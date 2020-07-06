package io.goooler.demoapp.base.model

import androidx.annotation.StringDef

@StringDef(
    RouterPath.LOGIN,
    RouterPath.MAIN
)
annotation class RouterPath {
    companion object {
        const val LOGIN: String = "/module/login"
        const val MAIN: String = "/module/main"
    }
}