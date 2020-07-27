package io.goooler.demoapp.main.repository

import io.goooler.demoapp.base.util.withIoContext
import io.goooler.demoapp.common.http.RetrofitHelper
import io.goooler.demoapp.common.util.getAllFromBox
import io.goooler.demoapp.common.util.putIntoBox
import io.goooler.demoapp.main.api.MainApi
import io.goooler.demoapp.main.api.RepoList
import io.goooler.demoapp.main.bean.RepoListBean

object MainRepository {

    private const val USER = "goooler"

    private val api by lazy {
        RetrofitHelper.createApiService(MainApi::class.java)
    }

    suspend fun getRepoListCr(user: String = USER) = api.getRepoListCr(user)

    fun getRepoListRx(user: String = USER) = api.getRepoListRx(user)

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