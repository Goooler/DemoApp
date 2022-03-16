package io.goooler.demoapp.detail.vm

import androidx.lifecycle.MutableLiveData
import io.goooler.demoapp.base.core.BaseViewModel
import io.goooler.demoapp.common.network.RetrofitHelper
import io.goooler.demoapp.detail.model.RepoDetailModel
import io.goooler.demoapp.detail.repository.DetailRepository

class DetailViewModel : BaseViewModel() {

  private val repository = DetailRepository(RetrofitHelper.create())
  private lateinit var _repoDetailModel: RepoDetailModel
  val repoDetailModel: MutableLiveData<RepoDetailModel> = MutableLiveData(RepoDetailModel())

  suspend fun getRepoDetail(owner: String = "Goooler", repo: String = "DemoApp") {
    repository.getRepoDetail(owner, repo).let {
      _repoDetailModel = RepoDetailModel(
        it.fullName,
        it.description,
        it.license.name,
        it.starsCount,
        it.forksCount,
        it.openIssuesCount
      )
    }
    repoDetailModel.value = _repoDetailModel
  }

  fun onFork() {
    _repoDetailModel = _repoDetailModel.copy()
    _repoDetailModel.forksCount++
    repoDetailModel.value = _repoDetailModel
  }
}
