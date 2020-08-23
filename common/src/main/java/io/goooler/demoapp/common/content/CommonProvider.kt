package io.goooler.demoapp.common.content

import androidx.core.content.FileProvider
import io.goooler.demoapp.base.util.log

class CommonProvider : FileProvider() {

    override fun onCreate(): Boolean {
        "CommonProvider".log()
        return super.onCreate()
    }
}