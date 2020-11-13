package io.goooler.demoapp.main.api

import io.goooler.demoapp.base.util.ParamMap
import io.goooler.demoapp.main.bean.MainRepoListBean
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

internal typealias RepoList = List<MainRepoListBean>

interface MainApi {

    @GET("users/{user}/repos")
    fun getRepoListRx(
        @Path("user") user: String,
        @QueryMap params: ParamMap
    ): Observable<RepoList>

    @GET("users/{user}/repos")
    suspend fun getRepoListCr(
        @Path("user") user: String,
        @QueryMap params: ParamMap
    ): RepoList
}