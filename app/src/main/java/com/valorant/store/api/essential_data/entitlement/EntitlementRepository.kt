package com.valorant.store.api.essential_data.entitlement

import com.valorant.store.api.Repository
import com.valorant.store.api.essential_data.entitlement.dto.EntitlementDTO

object EntitlementRepository : Repository<EntitlementApi>(EntitlementApi::class.java) {
    override val baseUrl = "https://entitlements.auth.riotgames.com"

    suspend fun getEntitlement() = try {
        val response = apiClient.entitlement()
        response.takeIf { it.isSuccessful }?.body()
            ?.let { EntitlementMapper.toEntitlementEntity(it) }
            ?: Result.failure(Exception("Null response body"))
    } catch (e: Exception) {
        Result.failure(e)
    }
}

object EntitlementMapper {
    fun toEntitlementEntity(entitlementDTO: EntitlementDTO): Result<EntitlementEntity> =
        EntitlementEntity.of(entitlementDTO.entitlementsToken).let { Result.success(it) }
}

data class EntitlementEntity(val entitlementToken: String) {
    companion object {
        fun of(entitlementToken: String) = EntitlementEntity(entitlementToken)
    }
}
