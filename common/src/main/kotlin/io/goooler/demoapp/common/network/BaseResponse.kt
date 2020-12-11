package io.goooler.demoapp.common.network

import androidx.annotation.Keep

@Keep
interface BaseResponse

class HttpResponse<T>(
  val status: Boolean,
  val message: String?,
  val code: Int,
  val count: Int,
  val entry: T? = null
) : BaseResponse
