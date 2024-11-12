package com.valorant.store.data.datasource.valInfo.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import com.fasterxml.jackson.databind.ObjectMapper
import com.valorant.store.data.datasource.LocalDatasource
import com.valorant.store.data.di.LocalDataModule.ValInfoDataStore
import com.valorant.store.data.di.UtilModule.DefaultObjectMapper
import com.valorant.store.data.model.currencies.local.CurrencyBatchCached
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class CurrenciesLocalDatasource @Inject constructor(
    @ValInfoDataStore private val valInfoDataStore: DataStore<Preferences>,
    @DefaultObjectMapper mapper: ObjectMapper
) : LocalDatasource(mapper) {
    val currenciesFlow: Flow<CurrencyBatchCached?> = valInfoDataStore.data
        .catch {
            if (it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences.getFromPreferences<CurrencyBatchCached>(DatastoreKey.CURRENCIES_CACHE)
        }

    suspend fun saveCurrencies(currenciesBatchCached: CurrencyBatchCached) {
        valInfoDataStore.writeCache(DatastoreKey.CURRENCIES_CACHE, currenciesBatchCached)
    }
}
