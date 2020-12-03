package io.goooler.demoapp.adapter.rv.paging

sealed class PagingSourceException : Exception() {
    class EmptyDataException : PagingSourceException()
    class NoMoreDataException : PagingSourceException()
}