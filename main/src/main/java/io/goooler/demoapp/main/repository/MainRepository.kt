package io.goooler.demoapp.main.repository

import io.goooler.demoapp.base.util.paramMapOf
import io.goooler.demoapp.common.network.RetrofitHelper
import io.goooler.demoapp.common.type.Constants
import io.goooler.demoapp.main.api.MainApi
import io.goooler.demoapp.main.api.RepoList
import io.reactivex.rxjava3.core.Observable

object MainRepository {

    private const val USER = "goooler"

    private val api = RetrofitHelper.create<MainApi>()

    suspend fun getRepoListCr(
        user: String = USER,
        page: Int = 1,
        pageSize: Int = Constants.defaultPageSize
    ): RepoList {
        val params = paramMapOf(
            "page" to page,
            "per_page" to pageSize
        )
        return api.getRepoListCr(user, params)
    }

    fun getRepoListRx(
        user: String = USER,
        page: Int = 1,
        pageSize: Int = Constants.defaultPageSize
    ): Observable<RepoList> {
        val params = paramMapOf(
            "page" to page,
            "per_page" to pageSize
        )
        return api.getRepoListRx(user, params)
    }
}