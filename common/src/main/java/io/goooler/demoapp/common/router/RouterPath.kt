package io.goooler.demoapp.common.router

import androidx.annotation.StringDef

@StringDef(
    RouterPath.LOGIN,
    RouterPath.MAIN
)
annotation class RouterPath {
    companion object {
        const val LOGIN: String = "/login/"
        const val MAIN: String = "/main/"
    }
}