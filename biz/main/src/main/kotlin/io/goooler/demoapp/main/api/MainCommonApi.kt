package io.goooler.demoapp.main.api

import io.goooler.demoapp.main.bean.MainRepoListBean
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface MainCommonApi {

  @GET("users/{user}/repos")
  suspend fun getRepoList(
    @Path("user") user: String,
    @QueryMap params: Map<String, Int>,
  ): List<MainRepoListBean>
}
