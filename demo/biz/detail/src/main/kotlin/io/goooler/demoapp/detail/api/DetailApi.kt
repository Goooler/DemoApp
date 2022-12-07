package io.goooler.demoapp.detail.api

import io.goooler.demoapp.detail.bean.RepoDetailBean
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailApi {

  @GET("https://api.github.com/repos/{fullName}")
  suspend fun getRepoDetail(
    @Path(value = "fullName", encoded = true) fullName: String,
  ): RepoDetailBean
}
