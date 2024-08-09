package com.valorant.store.api.riot.entitlement

import com.valorant.store.api.Repository
import com.valorant.store.api.riot.entitlement.dto.EntitlementDTO

object EntitlementRepository : Repository<EntitlementApi>(EntitlementApi::class.java) {
    override val baseUrl = "https://entitlements.auth.riotgames.com"

    suspend fun getEntitlement() = runCatching {
        val response = apiClient.entitlement()
        response.takeIf { it.isSuccessful }?.body()
            ?.let { EntitlementMapper.toEntitlementEntity(it) }
            ?: Result.failure(Exception("Null response body"))
    }.getOrElse { Result.failure(it) }
}

private object EntitlementMapper {
    fun toEntitlementEntity(entitlementDTO: EntitlementDTO): Result<EntitlementEntity> =
        EntitlementEntity(entitlementDTO.entitlementsToken).let { Result.success(it) }
}

data class EntitlementEntity(val entitlementToken: String)
