package io.goooler.demoapp.adapter.rv.paging

sealed class PagingSourceException : Exception() {
  data object EmptyDataException : PagingSourceException()
  data object NoMoreDataException : PagingSourceException()
}
