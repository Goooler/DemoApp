package io.goooler.demoapp.detail.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.goooler.demoapp.base.core.BaseViewModel
import io.goooler.demoapp.common.network.RetrofitHelper
import io.goooler.demoapp.detail.model.RepoDetailModel
import io.goooler.demoapp.detail.repository.DetailRepository
import kotlinx.coroutines.launch

class DetailViewModel : BaseViewModel() {

  private val repository = DetailRepository(RetrofitHelper.create())
  private var repoDetail = RepoDetailModel()
  private val _repoDetailModel = MutableLiveData(repoDetail)

  val repoDetailModel: LiveData<RepoDetailModel> = _repoDetailModel

  fun getRepoDetail(fullName: String) {
    viewModelScope.launch {
      repository.getRepoDetail(fullName).let {
        repoDetail = RepoDetailModel(
          it.fullName,
          it.description,
          it.license.name,
          it.starsCount,
          it.forksCount,
          it.openIssuesCount
        )
      }
      _repoDetailModel.value = repoDetail
    }
  }

  fun onFork() {
    repoDetail = repoDetail.copy()
    repoDetail.forksCount++
    _repoDetailModel.value = repoDetail
  }
}
