package io.goooler.demoapp.common.network

interface BaseResponse {
  val message: String? get() = null
  val code: Int get() = -1
}

class HttpResponse<T : Any>(val entry: T?) : BaseResponse
