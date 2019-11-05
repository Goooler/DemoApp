package io.goooler.demoapp.model

import androidx.annotation.StringDef

@StringDef(
        RouterPath.LOGIN
)
annotation class RouterPath {
    companion object {
        const val LOGIN: String = "module/login"
        const val MAIN: String = "module/main"
    }
}