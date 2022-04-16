package io.goooler.demoapp.detail.vm

import androidx.lifecycle.viewModelScope
import io.goooler.demoapp.common.base.theme.BaseThemeViewModel
import io.goooler.demoapp.common.network.RetrofitHelper
import io.goooler.demoapp.detail.model.RepoDetailModel
import io.goooler.demoapp.detail.repository.DetailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel : BaseThemeViewModel() {

  private val repository = DetailRepository(RetrofitHelper.create())
  private var repoDetail = RepoDetailModel()

  private val _repoDetailModel = MutableStateFlow(repoDetail)
  private val _isRefreshing = MutableStateFlow(false)

  lateinit var fullName: String

  val repoDetailModel: StateFlow<RepoDetailModel>
    get() = _repoDetailModel.asStateFlow()
  val isRefreshing: StateFlow<Boolean>
    get() = _isRefreshing.asStateFlow()

  fun refresh() {
    viewModelScope.launch {
      _isRefreshing.emit(true)
      repository.getRepoDetail(fullName).let {
        repoDetail = RepoDetailModel(
          it.fullName,
          it.description.orEmpty(),
          it.license?.name.orEmpty(),
          it.starsCount,
          it.forksCount,
          it.openIssuesCount
        )
      }
      _repoDetailModel.value = repoDetail
      _isRefreshing.emit(false)
    }
  }

  fun fork() {
    repoDetail = repoDetail.copy()
    repoDetail.forksCount++
    _repoDetailModel.value = repoDetail
  }
}
