package io.goooler.demoapp.common.util

import androidx.room.Room
import androidx.room.RoomDatabase
import io.goooler.demoapp.common.CommonApplication
import io.goooler.demoapp.common.type.Databases

object RoomHelper {
  const val DB_VERSION = 1

  inline fun <reified T : RoomDatabase> create(db: Databases = Databases.Demo): T {
    return Room.databaseBuilder(CommonApplication.app, T::class.java, db.dbName)
      .fallbackToDestructiveMigration()
      .build()
  }
}
