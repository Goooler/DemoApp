package io.goooler.demoapp.main.repository

import io.goooler.demoapp.base.util.paramMapOf
import io.goooler.demoapp.common.type.CommonConstants
import io.goooler.demoapp.main.api.MainCommonApi
import io.goooler.demoapp.main.bean.MainRepoListBean
import io.goooler.demoapp.main.db.MainDatabase
import io.reactivex.rxjava3.core.Observable

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

    suspend fun insertRepoListIntoDb(entityList: List<MainRepoListBean>) {
        db.mainDao.insertRepoList(*entityList.toTypedArray())
    }
}