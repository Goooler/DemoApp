package io.goooler.demoapp.main.api

import io.goooler.demoapp.main.bean.RepoListBean
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MainApi {

    @GET("users/{user}/repos")
    fun getRepoList(@Path("user") user: String): Call<List<RepoListBean>>

    @GET("users/{user}/repos")
    fun getRepoListRx(@Path("user") user: String): Single<List<RepoListBean>>

    @GET("users/{user}/repos")
    suspend fun getRepoListKt(@Path("user") user: String): List<RepoListBean>
}