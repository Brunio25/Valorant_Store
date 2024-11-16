package com.valorant.store.data.repository.contentTiers

import com.valorant.store.data.datasource.valInfo.local.ContentTiersLocalDatasource
import com.valorant.store.data.datasource.valInfo.remote.ValInfoRemoteDatasource
import com.valorant.store.data.mappers.contentTiers.toCached
import com.valorant.store.data.mappers.contentTiers.toDomain
import com.valorant.store.domain.model.contentTiers.ContentTierMap
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
class ContentTiersRepository @Inject constructor(
    private val valInfoRemoteDatasource: ValInfoRemoteDatasource,
    private val contentTiersLocalDatasource: ContentTiersLocalDatasource
) {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    val getContentTiersFlow: Flow<ContentTierMap?> = contentTiersLocalDatasource.contentTiersFlow
        .map { it?.toDomain() }
        .shareIn(
            scope = scope,
            started = SharingStarted.Lazily,
            replay = 1
        )

    suspend fun getContentTiers(): ContentTierMap? = getContentTiersFlow.firstOrNull()

    suspend fun reloadCacheFromRemote() {
        valInfoRemoteDatasource.getContentTiers()
            ?.let { contentTiersLocalDatasource.saveContentTiers(it.toCached()) }
    }
}
