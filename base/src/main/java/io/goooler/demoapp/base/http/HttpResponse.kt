package io.goooler.demoapp.base.http

import androidx.annotation.Keep

@Keep
interface BaseResponse

/**
 * 自定义的 http 返回结果
 */
class HttpResponse<T>(
    val status: Boolean,
    val message: String?,
    val code: Int,
    val count: Int,
    val entry: T? = null
) : BaseResponse