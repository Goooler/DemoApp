package io.goooler.demoapp.common.network

abstract class BaseResponse(
  val message: String? = null,
  val code: Int = -1,
)

class HttpResponse<T>(val entry: T?) : BaseResponse()
