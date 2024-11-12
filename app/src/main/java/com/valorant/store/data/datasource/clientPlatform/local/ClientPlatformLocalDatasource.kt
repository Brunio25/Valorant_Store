package com.valorant.store.data.datasource.clientPlatform.local

import com.fasterxml.jackson.databind.ObjectMapper
import com.valorant.store.data.datasource.LocalDatasource
import com.valorant.store.data.di.UtilModule.DefaultObjectMapper
import com.valorant.store.data.model.clientPlatform.ClientPlatformLocal
import javax.inject.Inject

class ClientPlatformLocalDatasource @Inject constructor(
    @DefaultObjectMapper mapper: ObjectMapper,
    private val clientPlatformLocal: ClientPlatformLocal = ClientPlatformLocal()
) : LocalDatasource(mapper) {
    fun getClientPlatform(): ClientPlatformLocal = clientPlatformLocal
}
