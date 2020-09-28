package io.goooler.demoapp.main.vm

import android.app.Application
import androidx.lifecycle.viewModelScope
import io.goooler.demoapp.common.base.BaseRxViewModel
import io.goooler.demoapp.main.model.MainListItemModel
import io.goooler.demoapp.main.repository.MainRepository
import kotlinx.coroutines.launch

class MainPagingViewModel(application: Application) : BaseRxViewModel(application) {

    private val _listData = mutableListOf<MainListItemModel>()
    private var page = 1

    fun refresh() {
        page = 1
        _listData.clear()
        fetchListData(page)
    }

    fun loadMore() {
        page++
        fetchListData(page)
    }

    private fun fetchListData(page: Int) {
        viewModelScope.launch {
            MainRepository.getRepoListCr("google", page)
        }
    }
}