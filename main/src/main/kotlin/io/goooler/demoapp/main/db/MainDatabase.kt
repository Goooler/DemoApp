package io.goooler.demoapp.main.db

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import io.goooler.demoapp.common.util.DataBaseHelper
import io.goooler.demoapp.main.bean.MainRepoListBean

@Database(entities = [MainRepoListBean::class], version = DataBaseHelper.DB_VERSION)
abstract class MainDatabase : RoomDatabase() {

    abstract val mainDao: MainDao

    companion object {
        @Volatile
        private var INSTANCE: MainDatabase? = null

        fun getInstance(context: Context): MainDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: DataBaseHelper.build<MainDatabase>(context).also { INSTANCE = it }
            }
        }
    }
}