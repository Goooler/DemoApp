package io.goooler.demoapp.main.vm

import dagger.hilt.android.lifecycle.HiltViewModel
import io.goooler.demoapp.base.util.MutableBooleanLiveData
import io.goooler.demoapp.base.util.MutableListLiveData
import io.goooler.demoapp.common.base.BaseRxViewModel
import io.goooler.demoapp.common.type.CommonConstants
import io.goooler.demoapp.main.model.MainCommonVhModel
import io.goooler.demoapp.main.repository.MainCommonRepository
import java.util.Collections
import javax.inject.Inject

@HiltViewModel
class MainSrlViewModel @Inject constructor(private val repository: MainCommonRepository) :
  BaseRxViewModel() {

  private val _listData = mutableListOf<MainCommonVhModel>()
  private var page = 1

  val listData = MutableListLiveData<MainCommonVhModel>()
  val isLoadMoreFinish = MutableBooleanLiveData()
  val isRefreshFinish = MutableBooleanLiveData()
  val isNoMore = MutableBooleanLiveData()
  val isEnableRefresh = MutableBooleanLiveData()
  val isEnableLoadMore = MutableBooleanLiveData()

  init {
    enableRefreshAndLoadMore(true)
  }

  fun refresh() {
    page = 1
    _listData.clear()
    fetchListData(page)
  }

  fun loadMore() {
    page++
    fetchListData(page)
  }

  fun swapItems(fromPosition: Int, toPosition: Int) {
    Collections.swap(_listData, fromPosition, toPosition)
    listData.value = _listData
  }

  fun deleteItem(position: Int) {
    _listData.removeAt(position)
    listData.value = _listData
  }

  private fun fetchListData(page: Int) {
    repository.getRepoListWithRx("goooler", page)
      .doFinally {
        isRefreshFinish.postValue(true)
        isLoadMoreFinish.postValue(true)
      }
      .map {
        it.map { bean -> MainCommonVhModel.Repo(bean.owner?.avatarUrl, bean.name) }
      }
      .doOnSuccess {
        _listData += it
        if (page == 1 && it.isEmpty()) {
          _listData += listOf(MainCommonVhModel.Empty())
        }
        if (it.size < CommonConstants.DEFAULT_PAGE_SIZE) {
          isNoMore.postValue(true)
        }
      }
      .subscribe(
        {
          listData.postValue(_listData)
        },
        {
          listData.postValue(listOf(MainCommonVhModel.Error()))
          enableRefreshAndLoadMore(false)
        }
      )
      .autoDispose()
  }

  private fun enableRefreshAndLoadMore(enable: Boolean) {
    isEnableRefresh.postValue(enable)
    isEnableLoadMore.postValue(enable)
  }
}
