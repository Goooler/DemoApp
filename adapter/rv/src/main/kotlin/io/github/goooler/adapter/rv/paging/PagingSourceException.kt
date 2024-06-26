package io.github.goooler.adapter.rv.paging

sealed class PagingSourceException : Exception() {
  data object EmptyDataException : PagingSourceException()
  data object NoMoreDataException : PagingSourceException()
}
