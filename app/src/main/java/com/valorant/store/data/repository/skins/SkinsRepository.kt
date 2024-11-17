package com.valorant.store.data.repository.skins

import com.valorant.store.data.datasource.valInfo.local.SkinsLocalDatasource
import com.valorant.store.data.datasource.valInfo.remote.ValInfoRemoteDatasource
import com.valorant.store.data.mappers.skins.toCached
import com.valorant.store.data.mappers.skins.toDomain
import com.valorant.store.domain.model.skins.SkinMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

class SkinsRepository @Inject constructor(
    private val valInfoRemoteDatasource: ValInfoRemoteDatasource,
    private val skinsLocalDatasource: SkinsLocalDatasource
) {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    val getSkinsFlow: Flow<SkinMap?> = skinsLocalDatasource.skinsFlow
        .map { it?.toDomain() }
        .shareIn(
            scope = scope,
            started = SharingStarted.Lazily,
            replay = 1
        )

    suspend fun getSkins(): SkinMap? = getSkinsFlow.firstOrNull()

    suspend fun reloadCacheFromRemote() {
        valInfoRemoteDatasource.getSkins()
            ?.let { skinsLocalDatasource.saveSkins(it.toCached()) }
    }
}
