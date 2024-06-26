package io.github.goooler.adapter.rv.paging

import androidx.annotation.IntRange
import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.github.goooler.adapter.rv.diff.IDiffVhModelType

public abstract class BasePagingSource<T : IDiffVhModelType> : PagingSource<Int, T>() {

  public override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> = try {
    val currentPage = params.key ?: 1
    val fetchedList = fetchListData(currentPage)
    if (fetchedList.isEmpty()) {
      throw if (currentPage == 1) {
        PagingSourceException.EmptyDataException
      } else {
        PagingSourceException.NoMoreDataException
      }
    } else {
      LoadResult.Page(
        fetchedList,
        if (currentPage == 1) null else currentPage - 1,
        currentPage + 1,
      )
    }
  } catch (@Suppress("TooGenericExceptionCaught") e: Exception) {
    LoadResult.Error(e)
  }

  public abstract suspend fun fetchListData(@IntRange(from = 1) page: Int): List<T>

  public override fun getRefreshKey(state: PagingState<Int, T>): Int? = null
}
