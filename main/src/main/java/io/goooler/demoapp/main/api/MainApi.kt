package io.goooler.demoapp.main.api

import io.goooler.demoapp.main.bean.RepoListBean
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

internal typealias RepoList = List<RepoListBean>

interface MainApi {

    @GET("users/{user}/repos")
    fun getRepoList(@Path("user") user: String): Call<RepoList>

    @GET("users/{user}/repos")
    fun getRepoListRx(@Path("user") user: String): Observable<RepoList>

    @GET("users/{user}/repos")
    suspend fun getRepoListCr(@Path("user") user: String): RepoList
}