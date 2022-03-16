package io.goooler.demoapp.detail.vm

import io.goooler.demoapp.base.core.BaseViewModel
import io.goooler.demoapp.common.network.RetrofitHelper
import io.goooler.demoapp.detail.model.RepoDetailModel
import io.goooler.demoapp.detail.repository.DetailRepository

class DetailViewModel : BaseViewModel() {

  private val repository = DetailRepository(RetrofitHelper.create())

  suspend fun getRepoDetail(owner: String, repo: String): RepoDetailModel {
    return repository.getRepoDetail(owner, repo).let {
      RepoDetailModel(
        it.description,
        it.license.name,
        it.starsCount,
        it.forksCount,
        it.openIssuesCount
      )
    }
  }
}
