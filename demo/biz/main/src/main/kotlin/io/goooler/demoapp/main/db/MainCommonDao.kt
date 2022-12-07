package io.goooler.demoapp.main.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.goooler.demoapp.main.bean.MainRepoListBean

@Dao
interface MainCommonDao {

  @Query("SELECT * FROM main_repo_list WHERE owner_name = :ownerName")
  suspend fun getRepoList(ownerName: String): List<MainRepoListBean>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertRepoList(list: List<MainRepoListBean>)
}
