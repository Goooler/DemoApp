package io.goooler.demoapp.main.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.goooler.demoapp.main.bean.MainRepoListBean

@Dao
interface MainDao {

    @Query("SELECT * FROM main_repo_list")
    suspend fun getRepoList(): List<MainRepoListBean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepoList(vararg entities: MainRepoListBean)
}