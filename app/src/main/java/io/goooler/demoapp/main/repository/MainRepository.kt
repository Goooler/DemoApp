package io.goooler.demoapp.main.repository

import io.goooler.demoapp.api.RetrofitHelper
import io.goooler.demoapp.main.api.MainApi
import io.goooler.demoapp.main.bean.RepoListBean
import io.goooler.demoapp.util.ObjectBox
import io.goooler.demoapp.util.putIntoBox
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Callback
import java.io.IOException

object MainRepository {

    private const val USER = "goooler"

    private val api by lazy {
        RetrofitHelper.createApiService(MainApi::class.java)
    }

    /**
     * execute 同步请求，手动回调
     * enqueue 异步请求，回调在主线程
     */
    suspend fun getRepoList(callback: Callback<List<RepoListBean>>) =
        withContext(Dispatchers.IO) {
            val call = api.getRepoList(USER)
            try {
                callback.onResponse(call, call.execute())
            } catch (e: IOException) {
                callback.onFailure(call, Throwable(e.message, e.cause))
            }
        }

    fun getRepoListRx(user: String = USER): Single<List<RepoListBean>> {
        return api.getRepoListRx(user)
    }

    suspend fun getRepoListCr(user: String = USER): List<RepoListBean> {
        return api.getRepoListCr(user)
    }

    /**
     * 数据库存储
     */
    suspend fun saveReposToDB(list: List<RepoListBean>) {
        withContext(Dispatchers.IO) {
            list.putIntoBox()
        }
    }

    /**
     * 数据库读取
     */
    suspend fun getReposFromDB(): List<RepoListBean> {
        return withContext(Dispatchers.IO) {
            ObjectBox.getAll<RepoListBean>()
        }
    }
}