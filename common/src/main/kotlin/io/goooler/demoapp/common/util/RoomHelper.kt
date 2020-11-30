package io.goooler.demoapp.common.util

import androidx.room.Room
import androidx.room.RoomDatabase
import io.goooler.demoapp.base.core.BaseApplication
import io.goooler.demoapp.common.type.Databases

@Suppress("UNCHECKED_CAST")
object RoomHelper {
    const val DB_VERSION = 1

    inline fun <reified T : RoomDatabase> create(db: Databases = Databases.Demo): T {
        return Room.databaseBuilder(BaseApplication.app, T::class.java, db.dbName)
            .fallbackToDestructiveMigration()
            .build()
    }
}