package io.goooler.demoapp.adapter.rv.paging

sealed class PagingSourceException : Exception() {
  object EmptyDataException : PagingSourceException()
  object NoMoreDataException : PagingSourceException()
}
