package com.valorant.store.data.datasource

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.fasterxml.jackson.databind.ObjectMapper
import com.valorant.store.data.extensions.readBase64Value
import com.valorant.store.data.extensions.writeValueAsBase64
import com.valorant.store.data.model.WrapperRemote
import retrofit2.Response

interface Client

interface Datasource

interface RemoteDatasource : Datasource {
    fun <T> Response<T>.getOrNull(): T? {
        val res = takeIf { it.isSuccessful }?.body()

        this@RemoteDatasource.javaClass.simpleName.also { className ->
            res?.also { Log.d(className, this.raw().request.url.toString()) }
                ?: Log.e(className, message())
        }

        return res
    }

    fun <W : WrapperRemote<T>, T> Response<W>.getOrNullWrapper(): T? = getOrNull()?.data
}

abstract class LocalDatasource(
    val mapper: ObjectMapper
) : Datasource {
    suspend fun <T> DataStore<Preferences>.writeCache(cacheKey: DatastoreKey, data: T) {
        val jsonData = mapper.writeValueAsBase64(data)
        edit { it[cacheKey.key] = jsonData }
    }

    inline fun <reified T> Preferences.getFromPreferences(cacheKey: DatastoreKey): T? =
        get(cacheKey.key)
            ?.let { mapper.readBase64Value<T>(it) }
            ?.also { Log.d(cacheKey.name, "CACHE_HIT") }
            ?: let {
                Log.d(cacheKey.name, "CACHE_MISS")
                null
            }

    enum class DatastoreKey(val key: Preferences.Key<String>) {
        VAL_API_VERSION(stringPreferencesKey("VAL_API_VERSION")),
        SKINS_CACHE(stringPreferencesKey("SKINS_CACHE")),
        CURRENCIES_CACHE(stringPreferencesKey("CURRENCIES_CACHE")),
        CONTENT_TIERS_CACHE(stringPreferencesKey("CONTENT_TIERS_CACHE")),
    }
}
