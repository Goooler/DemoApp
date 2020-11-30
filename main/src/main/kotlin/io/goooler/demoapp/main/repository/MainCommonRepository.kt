package io.goooler.demoapp.main.repository

import io.goooler.demoapp.base.util.paramMapOf
import io.goooler.demoapp.common.type.CommonConstants
import io.goooler.demoapp.common.util.fromJson
import io.goooler.demoapp.common.util.getFromDataStore
import io.goooler.demoapp.common.util.putIntoDataStore
import io.goooler.demoapp.common.util.toJson
import io.goooler.demoapp.main.api.MainCommonApi
import io.goooler.demoapp.main.bean.MainRepoListBean
import io.goooler.demoapp.main.db.MainDatabase
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MainCommonRepository(private val api: MainCommonApi, private val db: MainDatabase) {

    suspend fun getRepoListWithCr(
        user: String,
        page: Int = 1,
        pageSize: Int = CommonConstants.DEFAULT_PAGE_SIZE
    ): List<MainRepoListBean> {
        val params = paramMapOf(
            CommonConstants.RequestFields.PAGE to page,
            CommonConstants.RequestFields.PER_PAGE to pageSize
        )
        return api.getRepoListCr(user, params)
    }

    fun getRepoListWithRx(
        user: String,
        page: Int = 1,
        pageSize: Int = CommonConstants.DEFAULT_PAGE_SIZE
    ): Observable<List<MainRepoListBean>> {
        val params = paramMapOf(
            CommonConstants.RequestFields.PAGE to page,
            CommonConstants.RequestFields.PER_PAGE to pageSize
        )
        return api.getRepoListRx(user, params)
    }

    suspend fun getRepoListFromDb(): List<MainRepoListBean> = db.mainDao.getRepoList()

    suspend fun putRepoListIntoDb(list: List<MainRepoListBean>) {
        db.mainDao.insertRepoList(*list.toTypedArray())
    }

    suspend fun getRepoListFromDs(): Flow<List<MainRepoListBean>> {
        return KEY_REPO_LIST.getFromDataStore<String>().map {
            it?.fromJson(List::class.java, MainRepoListBean::class.java) ?: emptyList()
        }
    }

    suspend fun putRepoListIntoDs(list: List<MainRepoListBean>) {
        list.toJson().orEmpty().putIntoDataStore(KEY_REPO_LIST)
    }

    companion object {
        private const val KEY_REPO_LIST = "repo_list"
    }
}