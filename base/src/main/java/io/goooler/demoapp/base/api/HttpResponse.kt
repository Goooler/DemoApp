package io.goooler.demoapp.base.api

import androidx.annotation.Keep

/**
 * 自定义的 http 返回结果
 */
@Keep
class HttpResponse<T>(
    val status: Boolean,
    val message: String?,
    val responseCode: Int,
    val count: Int,
    val entry: T? = null
)