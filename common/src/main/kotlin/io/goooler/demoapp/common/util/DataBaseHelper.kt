package io.goooler.demoapp.common.util

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

object DataBaseHelper {

    const val DB_VERSION = 1

    inline fun <reified T : RoomDatabase> build(
        context: Context,
        dbName: String = "Demo.db"
    ): T {
        return Room.databaseBuilder(context.applicationContext, T::class.java, dbName)
            .fallbackToDestructiveMigration()
            .build()
    }
}