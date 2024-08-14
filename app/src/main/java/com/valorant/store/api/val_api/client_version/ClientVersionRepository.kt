package com.valorant.store.api.val_api.client_version

import com.valorant.store.api.Repository
import com.valorant.store.api.val_api.client_version.dto.ClientVersionDto

object ClientVersionRepository : Repository<ClientVersionApi>(ClientVersionApi::class.java, false) {
    override val baseUrl = "https://valorant-api.com/"

    suspend fun getClientVersion(): Result<ClientVersionEntity> = runCatching {
        val response = apiClient.clientVersion()
        response.takeIf { it.isSuccessful }?.body()
            ?.let { ClientVersionMapper.toClientVersionEntity(it) }
            ?: throw Exception("Null response body")
    }
}

private object ClientVersionMapper {
    fun toClientVersionEntity(clientVersionDto: ClientVersionDto): ClientVersionEntity =
        with(clientVersionDto.data) {
            ClientVersionEntity(
                riotVersion = riotClientVersion,
                valApiVersion = branch
            )
        }
}

data class ClientVersionEntity(val riotVersion: String, val valApiVersion: String)
