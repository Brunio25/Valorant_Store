package com.valorant.store.data.repository.clientVersion

import com.valorant.store.data.datasource.valInfo.local.ClientVersionLocalDatasource
import com.valorant.store.data.datasource.valInfo.remote.ValInfoRemoteDatasource
import com.valorant.store.data.mappers.clientVersion.toCached
import com.valorant.store.data.mappers.clientVersion.toDomain
import com.valorant.store.domain.model.clientVersion.ClientVersion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClientVersionRepository @Inject constructor(
    private val valInfoRemoteDatasource: ValInfoRemoteDatasource,
    private val clientVersionLocalDatasource: ClientVersionLocalDatasource
) {
    fun getClientVersionFlow(): Flow<ClientVersion?> =
        clientVersionLocalDatasource.clientVersionFlow.map { it?.toDomain() }

    suspend fun getClientVersion(): ClientVersion? = getClientVersionFlow().firstOrNull()

    suspend fun isVersionUpdated(): Boolean {
        val remoteVersionDeferred = withContext(Dispatchers.IO) {
            async { valInfoRemoteDatasource.getClientVersion() }
        }

        val localVersion = getClientVersion()?.valApiVersion

        val remoteVersion = remoteVersionDeferred.await()?.toDomain()?.valApiVersion ?: return true

        return remoteVersion == localVersion
    }

    suspend fun reloadCacheFromRemote() {
        valInfoRemoteDatasource.getClientVersion()
            ?.let { clientVersionLocalDatasource.saveClientVersion(it.toCached()) }
    }
}
