package com.valorant.store.global

import android.app.Application
import android.net.Uri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.valorant.store.api.config.ContentTierMapEntityCustomDeserializer
import com.valorant.store.api.config.CurrencyMapEntityCustomDeserializer
import com.valorant.store.api.config.SkinMapEntityCustomDeserializer
import com.valorant.store.api.config.UriCustomDeserializer
import com.valorant.store.api.val_api.skins.entity.ContentTierMapEntity
import com.valorant.store.api.val_api.skins.entity.CurrencyMapEntity
import com.valorant.store.api.val_api.skins.entity.SkinMapEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import java.lang.reflect.Type

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

    val gson: Gson = GsonBuilder()
        .registerTypeAdapter(Uri::class.java, UriCustomDeserializer())
        .registerTypeAdapter(typeToken<SkinMapEntity>(), SkinMapEntityCustomDeserializer())
        .registerTypeAdapter(typeToken<CurrencyMapEntity>(), CurrencyMapEntityCustomDeserializer())
        .registerTypeAdapter(typeToken<ContentTierMapEntity>(), ContentTierMapEntityCustomDeserializer())
        .create()

    fun initialize(application: Application) {
        this.application = application
    }

    fun <T> writeCache(cacheKey: DatastoreKey): suspend (T) -> Unit = { data: T ->
        val jsonData = gson.toJson(data)
        datastore.edit { it[cacheKey.key] = jsonData }
    }

    suspend fun <T> writeCache(cacheKey: DatastoreKey, data: T) = writeCache<T>(cacheKey)(data)

    suspend inline fun <reified T> readCache(
        cacheKey: DatastoreKey
    ): Result<T> = runCatching {
        datastore.data.first()[cacheKey.key]
            ?.let { gson.fromJson<T>(it, typeToken<T>()) }
            ?: throw NoSuchElementException("No cache for key $cacheKey")
    }

    suspend fun deleteCache(cacheKey: DatastoreKey) {
        datastore.edit { it -= cacheKey.key }
    }

    suspend inline fun <reified T> readOrWrite(
        cacheKey: DatastoreKey,
        defaultData: () -> T
    ): T = readCache<T>(cacheKey).getOrNull() ?: let {
        val data = defaultData()
        writeCache(cacheKey, data)
        data
    }

    suspend inline fun <reified T> getOrElse(
        cacheKey: DatastoreKey,
        onMiss: () -> T
    ): T = readCache<T>(cacheKey).getOrNull() ?: onMiss()

    inline fun <reified T> typeToken(): Type = object : TypeToken<T>() {}.type
}

enum class DatastoreKey(val key: Preferences.Key<String>) {
    VAL_API_VERSION(stringPreferencesKey("VAL_API_VERSION")),
    SKINS_CACHE(stringPreferencesKey("SKINS_CACHE")),
    CURRENCIES_CACHE(stringPreferencesKey("CURRENCIES_CACHE")),
    CONTENT_TIERS_CACHE(stringPreferencesKey("CONTENT_TIERS_CACHE"));
}
