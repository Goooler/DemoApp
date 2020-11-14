package io.goooler.demoapp.main.repository

import io.goooler.demoapp.base.util.paramMapOf
import io.goooler.demoapp.common.network.RetrofitHelper
import io.goooler.demoapp.common.type.CommonConstants
import io.goooler.demoapp.main.api.MainCommonApi
import io.goooler.demoapp.main.bean.MainRepoListBean
import io.reactivex.rxjava3.core.Observable

object MainCommonRepository {

    private val api: MainCommonApi = RetrofitHelper.create()

    suspend fun getRepoListCr(
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

    fun getRepoListRx(
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
}