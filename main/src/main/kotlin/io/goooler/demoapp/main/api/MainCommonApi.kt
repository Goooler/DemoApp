package io.goooler.demoapp.main.api

import io.goooler.demoapp.base.util.ParamMap
import io.goooler.demoapp.common.type.CommonConstants
import io.goooler.demoapp.main.bean.MainRepoListBean
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface MainCommonApi {

  @GET("users/{user}/repos")
  fun getRepoListWithRx(
    @Path("user") user: String,
    @Query(CommonConstants.RequestFields.PAGE) page: Int,
    @Query(CommonConstants.RequestFields.PER_PAGE) pageSize: Int
  ): Single<List<MainRepoListBean>>

  @GET("users/{user}/repos")
  suspend fun getRepoListWithCr(
    @Path("user") user: String,
    @QueryMap params: ParamMap
  ): List<MainRepoListBean>
}
