package com.valorant.store.data.repository.currencies

import com.valorant.store.data.datasource.valInfo.local.CurrenciesLocalDatasource
import com.valorant.store.data.datasource.valInfo.remote.ValInfoRemoteDatasource
import com.valorant.store.data.mappers.currencies.toCached
import com.valorant.store.data.mappers.currencies.toDomain
import com.valorant.store.domain.model.currencies.CurrencyMap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrenciesRepository @Inject constructor(
    private val valInfoRemoteDatasource: ValInfoRemoteDatasource,
    private val currenciesLocalDatasource: CurrenciesLocalDatasource
) {
    fun getCurrenciesFlow(): Flow<CurrencyMap?> = currenciesLocalDatasource.currenciesFlow
        .map { it?.toDomain() }

    suspend fun getCurrencies(): CurrencyMap? = getCurrenciesFlow().firstOrNull()

    suspend fun reloadCacheFromRemote() {
        valInfoRemoteDatasource.getCurrencies()
            ?.let { currenciesLocalDatasource.saveCurrencies(it.toCached()) }
    }
}
