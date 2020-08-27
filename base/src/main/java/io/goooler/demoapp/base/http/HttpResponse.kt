package io.goooler.demoapp.base.http

import androidx.annotation.Keep

@Keep
interface BaseBean

/**
 * 自定义的 http 返回结果
 */
class HttpResponse<T>(
    val status: Boolean,
    val message: String?,
    val responseCode: Int,
    val count: Int,
    val entry: T? = null
) : BaseBean