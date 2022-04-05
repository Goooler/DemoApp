package io.goooler.demoapp.main.api

import io.goooler.demoapp.base.util.ParamMap
import io.goooler.demoapp.main.bean.MainRepoListBean
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface MainCommonApi {

  @GET("users/{user}/repos")
  suspend fun getRepoListWithCr(
    @Path("user") user: String,
    @QueryMap params: ParamMap
  ): List<MainRepoListBean>
}
