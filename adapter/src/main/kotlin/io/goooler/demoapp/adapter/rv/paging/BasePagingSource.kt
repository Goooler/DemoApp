package io.goooler.demoapp.adapter.rv.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.goooler.demoapp.adapter.rv.diff.IDiffVhModelType

abstract class BasePagingSource<T : IDiffVhModelType> : PagingSource<Int, T>() {

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
    return try {
      val currentPage = params.key ?: 1
      val fetchedList = fetchListData(currentPage)
      if (fetchedList.isEmpty()) {
        if (currentPage == 1) {
          throw PagingSourceException.EmptyDataException()
        } else {
          throw PagingSourceException.NoMoreDataException()
        }
      } else {
        LoadResult.Page(
          fetchedList,
          if (currentPage == 1) null else currentPage - 1,
          currentPage + 1
        )
      }
    } catch (e: Exception) {
      LoadResult.Error(e)
    }
  }

  abstract suspend fun fetchListData(page: Int): List<T>

  override fun getRefreshKey(state: PagingState<Int, T>): Int? = null
}
