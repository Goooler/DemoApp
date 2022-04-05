package io.goooler.demoapp.main.repository

import androidx.annotation.IntRange
import io.goooler.demoapp.base.util.paramMapOf
import io.goooler.demoapp.common.type.CommonConstants
import io.goooler.demoapp.main.api.MainCommonApi
import io.goooler.demoapp.main.bean.MainRepoListBean
import io.goooler.demoapp.main.db.MainCommonDao
import javax.inject.Inject

class MainCommonRepository @Inject constructor(
  private val api: MainCommonApi,
  private val dao: MainCommonDao
) {

  suspend fun getRepoListWithCr(
    user: String,
    @IntRange(from = 1) page: Int = 1,
    @IntRange(from = 1) pageSize: Int = CommonConstants.DEFAULT_PAGE_SIZE
  ): List<MainRepoListBean> {
    val params = paramMapOf(
      "page" to page,
      "per_page" to pageSize
    )
    return api.getRepoListWithCr(user, params)
  }

  suspend fun getRepoListFromDb(ownerName: String): List<MainRepoListBean> =
    dao.getRepoList(ownerName)

  suspend fun putRepoListIntoDb(list: List<MainRepoListBean>) {
    dao.insertRepoList(*list.toTypedArray())
  }
}
