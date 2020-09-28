package io.goooler.demoapp.main.repository

import io.goooler.demoapp.base.util.withIoContext
import io.goooler.demoapp.common.type.Constants
import io.goooler.demoapp.common.util.getAllFromBox
import io.goooler.demoapp.common.util.getApiService
import io.goooler.demoapp.common.util.putIntoBox
import io.goooler.demoapp.main.api.MainApi
import io.goooler.demoapp.main.api.RepoList
import io.goooler.demoapp.main.bean.RepoListBean
import io.reactivex.rxjava3.core.Observable

object MainRepository {

    private const val USER = "goooler"

    private val api by getApiService<MainApi>()

    suspend fun getRepoListCr(user: String = USER): RepoList = api.getRepoListCr(user)

    fun getRepoListRx(
        user: String = USER,
        page: Int = 1,
        pageSize: Int = Constants.defaultPageSize
    ): Observable<RepoList> {
        val params = hashMapOf<String, Any>(
            "page" to page,
            "per_page" to pageSize
        )
        return api.getRepoListRx(user, params)
    }

    /**
     * 数据库存储
     */
    suspend fun saveReposToDB(list: RepoList) = withIoContext {
        list.putIntoBox()
    }

    /**
     * 数据库读取
     */
    suspend fun getReposFromDB() = withIoContext {
        RepoListBean::class.getAllFromBox()
    }
}