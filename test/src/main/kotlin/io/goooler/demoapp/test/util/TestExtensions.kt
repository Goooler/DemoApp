package io.goooler.demoapp.test.util

import io.goooler.demoapp.common.network.HttpResponse
import io.goooler.demoapp.test.network.exception.ResponseException
import io.goooler.demoapp.test.network.exception.toResponseException

@Throws(ResponseException::class)
fun <T> HttpResponse<T>.checkCodeWithException(): T? {
  if (code != 200) throw (message ?: code.toString()).toResponseException()
  return entry
}
