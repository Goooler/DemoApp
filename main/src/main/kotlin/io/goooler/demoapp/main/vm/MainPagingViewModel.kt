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
import io.goooler.demoapp.main.model.MainCommonRepoVhModel
import io.goooler.demoapp.main.model.MainCommonVhModel
import io.goooler.demoapp.main.repository.MainCommonRepository
import kotlinx.coroutines.flow.Flow

class MainPagingViewModel(application: Application) : BaseViewModel(application) {

    val listData: Flow<PagingData<MainCommonVhModel>> =
        Pager(PagingConfig(pageSize = Constants.defaultPageSize)) {
            DataSource()
        }.flow.cachedIn(viewModelScope)

    private inner class DataSource : BasePagingSource<MainCommonVhModel>() {
        override suspend fun fetchListData(page: Int): List<MainCommonVhModel> {
            return MainCommonRepository.getRepoListCr("google", page)
                .map { MainCommonRepoVhModel(it.owner?.avatarUrl, it.name) }
        }
    }
}