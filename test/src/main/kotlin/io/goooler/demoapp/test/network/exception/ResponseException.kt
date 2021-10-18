package io.goooler.demoapp.test.network.exception

import java.io.IOException
import retrofit2.HttpException

class ResponseException(message: String?, throwable: Throwable?) : Exception(message, throwable)

fun Throwable?.toResponseException(): ResponseException = when (this) {
  is ResponseException -> this
  is HttpException -> {
    val message = if (code() == 401) "auth failed" else "connect failed"
    ResponseException(message, this)
  }
  is IOException -> ResponseException("timeout", this)
  else -> ResponseException("unknown error", this)
}

fun String?.toResponseException(t: Throwable? = null): ResponseException =
  ResponseException(this, t)
