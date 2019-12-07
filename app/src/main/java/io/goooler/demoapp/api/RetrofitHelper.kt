package io.goooler.demoapp.api

import android.content.Context
import io.goooler.demoapp.base.BaseApplication
import io.goooler.demoapp.base.BaseRetrofitHelper

object RetrofitHelper : BaseRetrofitHelper() {

    override val baseUrl: String
        get() = "https://github.com"

    override val context: Context
        get() = BaseApplication.context
}