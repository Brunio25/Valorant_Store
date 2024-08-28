package com.valorant.store.global

import android.app.Application
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.valorant.store.extensions.readBase64Value
import com.valorant.store.extensions.writeValueAsBase64
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first

object AppCache {
    private const val CONTENT_CACHE_DATASTORE_NAME = "Valorant_Store_Content_Cache"

    private lateinit var application: Application
    val datastore: DataStore<Preferences> by lazy {
        PreferenceDataStoreFactory.create(
            produceFile = {
                application.applicationContext.preferencesDataStoreFile(CONTENT_CACHE_DATASTORE_NAME)
            },
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        )
    }

    val mapper: ObjectMapper = ObjectMapper().registerKotlinModule()

    fun initialize(application: Application) {
        this.application = application
    }

    fun <T> writeCache(cacheKey: DatastoreKey): suspend (T) -> Unit = { data: T ->
        val jsonData = mapper.writeValueAsBase64(data)
        datastore.edit { it[cacheKey.key] = jsonData }
    }

    suspend fun <T> writeCache(cacheKey: DatastoreKey, data: T) = writeCache<T>(cacheKey)(data)

    suspend inline fun <reified T> readCache(
        cacheKey: DatastoreKey
    ): Result<T> = runCatching {
        datastore.data.first()[cacheKey.key]
            ?.let { mapper.readBase64Value<T>(it) }
            ?.also { Log.d("CACHE_HIT", cacheKey.name) }
            ?: throw NoSuchElementException("No cache for key $cacheKey").also {
                Log.d("CACHE_MISS", cacheKey.name)
            }
    }

    suspend fun deleteCache(cacheKey: DatastoreKey) {
        datastore.edit { it -= cacheKey.key }
    }
}

enum class DatastoreKey(val key: Preferences.Key<String>) {
    VAL_API_VERSION(stringPreferencesKey("VAL_API_VERSION")),
    SKINS_CACHE(stringPreferencesKey("SKINS_CACHE")),
    CURRENCIES_CACHE(stringPreferencesKey("CURRENCIES_CACHE")),
    CONTENT_TIERS_CACHE(stringPreferencesKey("CONTENT_TIERS_CACHE"));
}
