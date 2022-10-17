package io.goooler.demoapp.common.network

sealed class BaseResponse(
  val message: String? = null,
  val code: Int = -1,
)

class HttpResponse<T>(val entry: T?) : BaseResponse()
