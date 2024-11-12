package com.valorant.store.data.repository.clientPlatform

import com.valorant.store.data.datasource.clientPlatform.local.ClientPlatformLocalDatasource
import com.valorant.store.data.mappers.clientPlatform.toDomain
import com.valorant.store.domain.model.clientPlatform.ClientPlatform
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClientPlatformRepository @Inject constructor(
    private val clientPlatformLocalDatasource: ClientPlatformLocalDatasource
) {
    fun getClientPlatform(): ClientPlatform =
        clientPlatformLocalDatasource.getClientPlatform().toDomain()
}
