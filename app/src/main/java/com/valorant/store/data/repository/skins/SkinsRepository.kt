package com.valorant.store.data.repository.skins

import com.valorant.store.data.datasource.valInfo.local.SkinsLocalDatasource
import com.valorant.store.data.datasource.valInfo.remote.ValInfoRemoteDatasource
import com.valorant.store.data.mappers.skins.toCached
import com.valorant.store.data.mappers.skins.toDomain
import com.valorant.store.domain.model.skins.SkinMap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SkinsRepository @Inject constructor(
    private val valInfoRemoteDatasource: ValInfoRemoteDatasource,
    private val skinsLocalDatasource: SkinsLocalDatasource
) {
    fun getSkinsFlow(): Flow<SkinMap?> = skinsLocalDatasource.skinsFlow.map { it?.toDomain() }

    suspend fun getSkins(): SkinMap? = getSkinsFlow().firstOrNull()

    suspend fun reloadCacheFromRemote() {
        valInfoRemoteDatasource.getSkins()
            ?.let { skinsLocalDatasource.saveSkins(it.toCached()) }
    }
}
