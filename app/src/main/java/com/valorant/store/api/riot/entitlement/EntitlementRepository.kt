package com.valorant.store.api.riot.entitlement

import com.valorant.store.api.Repository
import com.valorant.store.api.riot.entitlement.dto.EntitlementDTO

object EntitlementRepository : Repository<EntitlementApi>(EntitlementApi::class.java) {
    override val baseUrl = "https://entitlements.auth.riotgames.com"

    suspend fun getEntitlement(): Result<EntitlementEntity> = runCatching {
        val response = apiClient.entitlement()

        response.takeIf { it.isSuccessful }
            ?.body()
            ?.let { EntitlementMapper.toEntitlementEntity(it) }
            ?: throw Exception("Null response body")
    }
}

private object EntitlementMapper {
    fun toEntitlementEntity(entitlementDTO: EntitlementDTO): EntitlementEntity =
        EntitlementEntity(entitlementDTO.entitlementsToken)
}

data class EntitlementEntity(val entitlementToken: String)
