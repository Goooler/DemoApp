package io.goooler.demoapp

import io.goooler.demoapp.base.util.ObjectBox
import io.goooler.demoapp.common.RouterApplication
import io.goooler.demoapp.main.bean.MyObjectBox

class DemoApplication : RouterApplication() {

    override fun onCreate() {
        super.onCreate()

        ObjectBox.init {
            MyObjectBox.builder()
                .androidContext(this)
                .build()
        }
    }
}