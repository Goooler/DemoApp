package io.goooler.demoapp.detail.api

import retrofit2.http.GET
import retrofit2.http.Path

interface DetailApi {

  @GET("https://api.github.com/repos/{owner}/{repo}")
  suspend fun getRepoDetail(@Path("owner") owner: String, @Path("repo") repo: String)
}
