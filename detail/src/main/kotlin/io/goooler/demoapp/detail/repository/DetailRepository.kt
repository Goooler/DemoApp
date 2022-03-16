package io.goooler.demoapp.detail.repository

import io.goooler.demoapp.detail.api.DetailApi
import io.goooler.demoapp.detail.bean.RepoDetailBean

class DetailRepository(private val api: DetailApi) {

  suspend fun getRepoDetail(owner: String, repo: String): RepoDetailBean =
    api.getRepoDetail(owner, repo)
}
