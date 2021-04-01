package io.goooler.demoapp.common.network

import androidx.annotation.Keep

@Keep
abstract class BaseResponse {
  var status: Boolean = false
  var message: String? = null
  var code: Int = 0
  var count: Int = 0
}

class HttpResponse<T>(val entry: T?) : BaseResponse()
