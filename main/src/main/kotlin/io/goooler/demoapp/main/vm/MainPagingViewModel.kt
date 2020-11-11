package io.goooler.demoapp.main.vm

import android.app.Application
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import io.goooler.demoapp.adapter.rv.paging.BasePagingSource
import io.goooler.demoapp.base.core.BaseViewModel
import io.goooler.demoapp.common.type.Constants
import io.goooler.demoapp.main.model.MainListItemModel
import io.goooler.demoapp.main.repository.MainRepository
import kotlinx.coroutines.flow.Flow

class MainPagingViewModel(application: Application) : BaseViewModel(application) {

    val listData: Flow<PagingData<MainListItemModel>> =
        Pager(PagingConfig(pageSize = Constants.defaultPageSize)) {
            DataSource()
        }.flow.cachedIn(viewModelScope)

    private inner class DataSource : BasePagingSource<MainListItemModel>() {
        override suspend fun fetchListData(page: Int): List<MainListItemModel> {
            return MainRepository.getRepoListCr("google", page)
                .map { MainListItemModel(it.owner?.avatarUrl, it.name) }
        }
    }
}