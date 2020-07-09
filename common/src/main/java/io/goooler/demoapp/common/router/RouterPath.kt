package io.goooler.demoapp.common.router

import androidx.annotation.StringDef

@StringDef(
    RouterPath.LOGIN,
    RouterPath.MAIN,
    RouterPath.WEB
)
annotation class RouterPath {
    companion object {
        const val LOGIN = "/login/"
        const val MAIN = "/main/"
        const val WEB = "/web/"
    }
}