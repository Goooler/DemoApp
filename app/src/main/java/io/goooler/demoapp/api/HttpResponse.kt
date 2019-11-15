package io.goooler.demoapp.api

import androidx.annotation.Keep

@Keep
class HttpResponse<T>(val status: Boolean, val message: String?, val responseCode: Int, val count: Int, val entry: T? = null)