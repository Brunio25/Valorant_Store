package com.valorant.store.data.repository.currencies

import com.valorant.store.data.datasource.valInfo.local.CurrenciesLocalDatasource
import com.valorant.store.data.datasource.valInfo.remote.ValInfoRemoteDatasource
import com.valorant.store.data.mappers.currencies.toCached
import com.valorant.store.data.mappers.currencies.toDomain
import com.valorant.store.domain.model.currencies.CurrencyMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrenciesRepository @Inject constructor(
    private val valInfoRemoteDatasource: ValInfoRemoteDatasource,
    private val currenciesLocalDatasource: CurrenciesLocalDatasource
) {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    val getCurrenciesFlow: Flow<CurrencyMap?> = currenciesLocalDatasource.currenciesFlow
        .map { it?.toDomain() }
        .shareIn(
            scope = scope,
            started = SharingStarted.Lazily,
            replay = 1
        )

    suspend fun getCurrencies(): CurrencyMap? = getCurrenciesFlow.firstOrNull()

    suspend fun reloadCacheFromRemote() {
        valInfoRemoteDatasource.getCurrencies()
            ?.let { currenciesLocalDatasource.saveCurrencies(it.toCached()) }
    }
}
