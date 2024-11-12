package com.valorant.store.data.repository.contentTiers

import com.valorant.store.data.datasource.valInfo.local.ContentTiersLocalDatasource
import com.valorant.store.data.datasource.valInfo.remote.ValInfoRemoteDatasource
import com.valorant.store.data.mappers.contentTiers.toCached
import com.valorant.store.data.mappers.contentTiers.toDomain
import com.valorant.store.domain.model.contentTiers.ContentTierMap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentTiersRepository @Inject constructor(
    private val valInfoRemoteDatasource: ValInfoRemoteDatasource,
    private val contentTiersLocalDatasource: ContentTiersLocalDatasource
) {
    fun getContentTiersFlow(): Flow<ContentTierMap?> =
        contentTiersLocalDatasource.contentTiersFlow.map { cachedData -> cachedData?.toDomain() }

    suspend fun getContentTiers(): ContentTierMap? = getContentTiersFlow().firstOrNull()

    suspend fun reloadCacheFromRemote() {
        valInfoRemoteDatasource.getContentTiers()
            ?.let { contentTiersLocalDatasource.saveContentTiers(it.toCached()) }
    }
}
