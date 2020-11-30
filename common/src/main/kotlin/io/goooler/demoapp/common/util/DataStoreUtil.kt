package io.goooler.demoapp.common.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.createDataStore

object DataStoreUtil {

    private lateinit var context: Context
    private const val PREFERENCE_NAME = "DataStore"
    private const val DEFAULT_SP_NAME = "spUtils"

    fun init(context: Context) {
        this.context = context
    }

    fun getDataStore(
        name: String = PREFERENCE_NAME,
        spName: String = DEFAULT_SP_NAME
    ): DataStore<Preferences> {
        return context.createDataStore(
            name,
            migrations = listOf(SharedPreferencesMigration(context, spName))
        )
    }
}