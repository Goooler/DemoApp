package io.goooler.demoapp.main.vm

import android.app.Application
import io.goooler.demoapp.base.util.MutableBooleanLiveData
import io.goooler.demoapp.base.util.MutableListLiveData
import io.goooler.demoapp.common.base.BaseRxViewModel
import io.goooler.demoapp.common.type.CommonConstants
import io.goooler.demoapp.common.util.toastThrowable
import io.goooler.demoapp.main.model.MainCommonRepoVhModel
import io.goooler.demoapp.main.model.MainCommonVhModel
import io.goooler.demoapp.main.repository.MainCommonRepository

class MainSrlViewModel(application: Application) : BaseRxViewModel(application) {

    private val repository = MainCommonRepository(application)
    private val _listData = mutableListOf<MainCommonVhModel>()
    private var page = 1

    val listData = MutableListLiveData<MainCommonVhModel>()
    val isLoadMoreFinish = MutableBooleanLiveData()
    val isRefreshFinish = MutableBooleanLiveData()
    val isNoMore = MutableBooleanLiveData()
    val isEnableRefresh = MutableBooleanLiveData(true)
    val isEnableLoadMore = MutableBooleanLiveData(true)

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
        repository.getRepoListWithRx("google", page)
            .doFinally(::finishRefreshAndLoadMore)
            .map {
                it.map { bean -> MainCommonRepoVhModel(bean.owner?.avatarUrl, bean.name) }
            }
            .doOnNext {
                _listData.addAll(it)
                if (it.size < CommonConstants.DEFAULT_PAGE_SIZE) {
                    isEnableLoadMore.postValue(false)
                    isNoMore.postValue(true)
                }
            }
            .subscribe({
                listData.postValue(_listData)
            }, ::toastThrowable)
            .autoDispose()
    }

    private fun finishRefreshAndLoadMore() {
        isRefreshFinish.postValue(true)
        isLoadMoreFinish.postValue(true)
    }
}