package io.github.goooler.adapter.rv.paging

public sealed class PagingSourceException : Exception() {
  public data object EmptyDataException : PagingSourceException()
  public data object NoMoreDataException : PagingSourceException()
}
