package io.goooler.demoapp.common.util

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

object DatabaseHelper {

    const val DB_VERSION = 1
    const val DB_NAME = "Demo.db"

    inline fun <reified T : RoomDatabase> build(
        context: Context,
        dbName: String = DB_NAME
    ): T {
        return Room.databaseBuilder(context.applicationContext, T::class.java, dbName)
            .fallbackToDestructiveMigration()
            .build()
    }
}

inline fun <reified T : RoomDatabase> buildDatabase(
    context: Context,
    dbName: String = DatabaseHelper.DB_NAME
): Lazy<T> = lazy {
    DatabaseHelper.build(context, dbName)
}