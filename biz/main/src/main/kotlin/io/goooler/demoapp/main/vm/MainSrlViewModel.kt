package io.goooler.demoapp.main.vm

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.goooler.demoapp.common.base.theme.BaseThemeViewModel
import io.goooler.demoapp.common.type.CommonConstants
import io.goooler.demoapp.main.model.MainCommonVhModel
import io.goooler.demoapp.main.repository.MainCommonRepository
import java.util.Collections
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MainSrlViewModel @Inject constructor(private val repository: MainCommonRepository) :
  BaseThemeViewModel() {

  private val _listData = mutableListOf<MainCommonVhModel>()
  private var page = 1

  val listData: MutableStateFlow<List<MainCommonVhModel>> = MutableStateFlow(emptyList())
  val isLoadMoreFinish = MutableStateFlow(false)
  val isRefreshFinish = MutableStateFlow(false)
  val isNoMore = MutableStateFlow(false)
  val isEnableRefresh = MutableStateFlow(false)
  val isEnableLoadMore = MutableStateFlow(false)

  init {
    enableRefreshAndLoadMore(true)
  }

  fun refresh() {
    page = 1
    isNoMore.value = false
    _listData.clear()
    fetchListData(page)
  }

  fun loadMore() {
    page++
    fetchListData(page)
  }

  fun swapItems(fromPosition: Int, toPosition: Int) {
    Collections.swap(_listData, fromPosition, toPosition)
    listData.value = _listData.toList()
  }

  fun deleteItem(position: Int) {
    _listData.removeAt(position)
    listData.value = _listData.toList()
  }

  fun share(fullName: String) {
    viewModelScope.launch(Dispatchers.Default) {
      val list = mutableListOf<MainCommonVhModel>()
      _listData.forEach { model ->
        list += if (model is MainCommonVhModel.Repo && model.fullName == fullName) {
          model.copy(shareCount = model.shareCount + 1)
        } else
          model
      }
      _listData.clear()
      _listData += list
      listData.value = list
    }
  }

  private fun fetchListData(page: Int) {
    viewModelScope.launch {
      try {
        finishRefreshAndLoadMore(false)
        repository.getRepoListFromApi("goooler", page)
          .map { bean ->
            MainCommonVhModel.Repo(bean.owner?.avatarUrl, bean.name, bean.fullName)
          }.let {
            _listData += it
            if (page == 1 && it.isEmpty()) {
              _listData += listOf(MainCommonVhModel.Empty())
            }
            if (it.size < CommonConstants.DEFAULT_PAGE_SIZE) {
              isNoMore.value = true
            }
            _listData.toList()
          }.let {
            listData.value = it
          }
      } catch (_: Exception) {
        listData.value = listOf(MainCommonVhModel.Error())
        enableRefreshAndLoadMore(false)
      } finally {
        finishRefreshAndLoadMore(true)
      }
    }
  }

  private fun finishRefreshAndLoadMore(finish: Boolean) {
    isRefreshFinish.value = finish
    isLoadMoreFinish.value = finish
  }

  private fun enableRefreshAndLoadMore(enable: Boolean) {
    isEnableRefresh.value = enable
    isEnableLoadMore.value = enable
  }
}
