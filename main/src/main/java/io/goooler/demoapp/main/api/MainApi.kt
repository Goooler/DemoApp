package io.goooler.demoapp.main.api

import io.goooler.demoapp.main.bean.RepoListBean
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

internal typealias RepoList = List<RepoListBean>

interface MainApi {

    @GET("users/{user}/repos")
    fun getRepoListRx(
        @Path("user") user: String,
        @QueryMap params: HashMap<String, Any>
    ): Observable<RepoList>

    @GET("users/{user}/repos")
    suspend fun getRepoListCr(
        @Path("user") user: String,
        @QueryMap params: HashMap<String, Any>
    ): RepoList
}