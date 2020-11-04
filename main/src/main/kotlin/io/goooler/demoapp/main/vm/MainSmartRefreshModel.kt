package io.goooler.demoapp.main.vm

import android.app.Application
import io.goooler.demoapp.base.util.MutableBooleanLiveData
import io.goooler.demoapp.base.util.MutableListLiveData
import io.goooler.demoapp.common.base.BaseRxViewModel
import io.goooler.demoapp.common.type.Constants
import io.goooler.demoapp.common.util.toastThrowable
import io.goooler.demoapp.main.model.MainListItemModel
import io.goooler.demoapp.main.repository.MainRepository

class MainSmartRefreshModel(application: Application) : BaseRxViewModel(application) {

    val listData = MutableListLiveData<MainListItemModel>()
    val isLoadMoreFinish = MutableBooleanLiveData()
    val isRefreshFinish = MutableBooleanLiveData()
    val isNoMore = MutableBooleanLiveData()
    val isEnableRefresh = MutableBooleanLiveData(true)
    val isEnableLoadMore = MutableBooleanLiveData(true)

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
        MainRepository.getRepoListRx("google", page)
            .map {
                it.map { bean -> MainListItemModel(bean.owner?.avatarUrl, bean.name) }
            }
            .doOnNext {
                _listData.addAll(it)
                if (it.size < Constants.defaultPageSize) {
                    isEnableLoadMore.value = false
                    isNoMore.value = true
                }
            }
            .doFinally {
                finishRefreshAndLoadMore()
            }
            .subscribe({
                listData.postValue(_listData)
            }, {
                toastThrowable(it)
            }).add()
    }

    private fun finishRefreshAndLoadMore() {
        isRefreshFinish.postValue(true)
        isLoadMoreFinish.postValue(true)
    }
}