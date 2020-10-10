package io.goooler.demoapp.main.vm

import android.app.Application
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import io.goooler.demoapp.base.core.BaseViewModel
import io.goooler.demoapp.common.type.Constants
import io.goooler.demoapp.main.model.MainListItemModel
import io.goooler.demoapp.main.repository.MainRepository
import kotlinx.coroutines.flow.Flow

class MainPagingViewModel(application: Application) : BaseViewModel(application) {

    val listData: Flow<PagingData<MainListItemModel>> =
        Pager(PagingConfig(pageSize = Constants.defaultPageSize)) {
            PagingDataSource()
        }.flow.cachedIn(viewModelScope)

    private inner class PagingDataSource : PagingSource<Int, MainListItemModel>() {
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MainListItemModel> {
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
    }

    private suspend fun fetchListData(page: Int): List<MainListItemModel> {
        return MainRepository.getRepoListCr("google", page)
            .map { bean -> MainListItemModel(bean.owner?.avatarUrl, bean.name) }
    }
}