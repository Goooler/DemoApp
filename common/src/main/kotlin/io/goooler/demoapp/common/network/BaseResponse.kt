package io.goooler.demoapp.common.network

import androidx.annotation.Keep

@Keep
abstract class BaseResponse(
  val message: String? = null,
  val code: Int = -1,
)

class HttpResponse<T>(val entry: T?) : BaseResponse()
