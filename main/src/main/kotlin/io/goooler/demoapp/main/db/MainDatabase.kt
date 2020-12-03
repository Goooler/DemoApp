package io.goooler.demoapp.main.db

import androidx.room.Database
import androidx.room.RoomDatabase
import io.goooler.demoapp.common.util.RoomHelper
import io.goooler.demoapp.main.bean.MainRepoListBean

@Database(
    entities = [MainRepoListBean::class],
    version = RoomHelper.DB_VERSION
)
abstract class MainDatabase : RoomDatabase() {

    abstract val mainCommonDao: MainCommonDao
}