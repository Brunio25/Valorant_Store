package com.valorant.store.api.essential_data.client_version

import com.valorant.store.api.Repository
import com.valorant.store.api.essential_data.client_version.dto.ClientVersionDto

object ClientVersionRepository : Repository<ClientVersionApi>(ClientVersionApi::class.java) {
    override val baseUrl = "https://valorant-api.com/"

    suspend fun getClientVersion() = try {
        val response = apiClient.clientVersion()
        response.takeIf { it.isSuccessful }?.body()
            ?.let { ClientVersionMapper.toClientVersionEntity(it) }
            ?: Result.failure(Exception("Null response body"))
    } catch (e: Exception) {
        Result.failure(e)
    }
}

object ClientVersionMapper {
    fun toClientVersionEntity(clientVersionDto: ClientVersionDto): Result<ClientVersionEntity> =
        ClientVersionEntity.of(clientVersionDto.data.version).let { Result.success(it) }
}

data class ClientVersionEntity(val version: String) {
    companion object {
        fun of(version: String) = ClientVersionEntity(version)
    }
}
