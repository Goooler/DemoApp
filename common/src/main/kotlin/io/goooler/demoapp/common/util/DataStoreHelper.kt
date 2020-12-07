package io.goooler.demoapp.common.util

import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.emptyPreferences
import androidx.datastore.preferences.preferencesKey
import io.goooler.demoapp.base.core.BaseApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.util.concurrent.ConcurrentHashMap

class DataStoreHelper private constructor(name: String) {

    private val dataStore = BaseApplication.app.createDataStore(name)

    suspend fun <T> get(key: Preferences.Key<T>): Flow<T?> {
        return dataStore.data
            .catch {
                it.printStackTrace()
                emit(emptyPreferences())
            }
            .map {
                it[key]
            }
    }

    suspend inline fun <reified T : Any> get(key: String): Flow<T?> = get(preferencesKey<T>(key))

    suspend fun <T> put(key: Preferences.Key<T>, value: T) {
        try {
            dataStore.edit {
                it[key] = value
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend inline fun <reified T : Any> put(key: String, value: T) {
        put(preferencesKey(key), value)
    }

    companion object {
        private val helperMap = ConcurrentHashMap<String, DataStoreHelper>()

        fun getInstance(name: String): DataStoreHelper {
            return helperMap[name] ?: DataStoreHelper(name).also {
                helperMap[name] = it
            }
        }
    }
}