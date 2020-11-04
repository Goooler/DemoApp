package io.goooler.demoapp.adapter.rv.datasource

import androidx.paging.PagingSource
import io.goooler.demoapp.adapter.rv.base.IModelType

abstract class BasePagingSource<T : IModelType> : PagingSource<Int, T>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val currentPage = params.key ?: 1
            LoadResult.Page(
                fetchListData(currentPage),
                if (currentPage == 1) null else currentPage - 1,
                currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    abstract suspend fun fetchListData(page: Int): List<T>
}