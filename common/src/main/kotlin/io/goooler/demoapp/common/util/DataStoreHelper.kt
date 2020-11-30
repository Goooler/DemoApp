package io.goooler.demoapp.common.util

import androidx.datastore.preferences.*
import io.goooler.demoapp.base.core.BaseApplication
import io.goooler.demoapp.common.util.DataStoreHelper.Companion.PREFERENCE_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

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
        const val PREFERENCE_NAME = "demo_app"
        private val HELPER_MAP = hashMapOf<String, DataStoreHelper>()

        fun getInstance(name: String = PREFERENCE_NAME): DataStoreHelper {
            return HELPER_MAP[name] ?: DataStoreHelper(name).also {
                HELPER_MAP[name] = it
            }
        }
    }
}

suspend inline fun <reified T : Any> String.getFromDataStore(prefName: String = PREFERENCE_NAME): Flow<T?> {
    return DataStoreHelper.getInstance(prefName).get(this)
}

suspend inline fun <reified T : Any> T.putIntoDataStore(
    key: String,
    prefName: String = PREFERENCE_NAME
) {
    DataStoreHelper.getInstance(prefName).put(key, this)
}