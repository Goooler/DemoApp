package io.goooler.demoapp.detail.vm

import androidx.lifecycle.MutableLiveData
import io.goooler.demoapp.base.core.BaseViewModel
import io.goooler.demoapp.common.network.RetrofitHelper
import io.goooler.demoapp.detail.model.RepoDetailModel
import io.goooler.demoapp.detail.repository.DetailRepository

class DetailViewModel : BaseViewModel() {

  private val repository = DetailRepository(RetrofitHelper.create())

  val repoDetailModel: MutableLiveData<RepoDetailModel> = MutableLiveData()

  suspend fun getRepoDetail(owner: String = "Goooler", repo: String = "DemoApp") {
    repository.getRepoDetail(owner, repo).let {
      repoDetailModel.value = RepoDetailModel(
        it.description,
        it.license.name,
        it.starsCount,
        it.forksCount,
        it.openIssuesCount
      )
    }
  }
}
