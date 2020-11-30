package io.goooler.demoapp.main.repository

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesKey
import io.goooler.demoapp.base.util.paramMapOf
import io.goooler.demoapp.common.type.CommonConstants
import io.goooler.demoapp.common.util.DataStoreUtil
import io.goooler.demoapp.common.util.fromJson
import io.goooler.demoapp.common.util.toJson
import io.goooler.demoapp.main.api.MainCommonApi
import io.goooler.demoapp.main.bean.MainRepoListBean
import io.goooler.demoapp.main.db.MainDatabase
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class MainCommonRepository(private val api: MainCommonApi, private val db: MainDatabase) {

    private val dsRepoListKey = preferencesKey<String>("repo_list")

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

    suspend fun insertRepoListIntoDb(list: List<MainRepoListBean>) {
        db.mainDao.insertRepoList(*list.toTypedArray())
    }

    suspend fun getRepoListFromDs(): Flow<List<MainRepoListBean>> {
        return DataStoreUtil.getDataStore().data
            .catch {
                emit(emptyPreferences())
            }
            .map {
                it[dsRepoListKey]?.fromJson<List<MainRepoListBean>>() ?: emptyList()
            }
    }

    suspend fun storeRepoListToDs(list: List<MainRepoListBean>) {
        DataStoreUtil.getDataStore().edit {
            it[dsRepoListKey] = list.toJson().orEmpty()
        }
    }
}