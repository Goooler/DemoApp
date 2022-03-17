package io.goooler.demoapp.detail.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.goooler.demoapp.base.core.BaseViewModel
import io.goooler.demoapp.base.util.MutableBooleanLiveData
import io.goooler.demoapp.common.network.RetrofitHelper
import io.goooler.demoapp.detail.model.RepoDetailModel
import io.goooler.demoapp.detail.repository.DetailRepository
import kotlinx.coroutines.launch

class DetailViewModel : BaseViewModel() {

  private val repository = DetailRepository(RetrofitHelper.create())
  private var repoDetail = RepoDetailModel()
  private val _repoDetailModel = MutableLiveData(repoDetail)

  lateinit var fullName: String
  val repoDetailModel: LiveData<RepoDetailModel> = _repoDetailModel
  val isRefreshing: MutableBooleanLiveData = MutableBooleanLiveData()

  fun refresh() {
    isRefreshing.value = true
    viewModelScope.launch {
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
      isRefreshing.value = false
    }
  }

  fun fork() {
    repoDetail = repoDetail.copy()
    repoDetail.forksCount++
    _repoDetailModel.value = repoDetail
  }
}
