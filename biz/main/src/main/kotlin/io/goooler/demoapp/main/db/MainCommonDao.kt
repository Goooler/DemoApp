package io.goooler.demoapp.main.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Upsert
import io.goooler.demoapp.main.bean.MainRepoListBean

@Dao
interface MainCommonDao {

  @RewriteQueriesToDropUnusedColumns
  @Query("SELECT * FROM main_repo_list WHERE owner_name = :ownerName")
  suspend fun getRepoList(ownerName: String): List<MainRepoListBean>

  @Upsert
  suspend fun upsertRepoList(list: List<MainRepoListBean>)
}
