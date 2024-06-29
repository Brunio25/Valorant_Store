package com.valorant.store.api.entitlement

import com.valorant.store.api.Repository
import com.valorant.store.api.entitlement.dto.EntitlementDTO

private const val BASE_URL = "https://entitlements.auth.riotgames.com"

object EntitlementRepository : Repository<EntitlementApi>(EntitlementApi::class.java, BASE_URL) {
    private val entitlementMapper = EntitlementMapper
    suspend fun getEntitlement() = try {
        val response = apiClient.entitlement()
        response.takeIf { it.isSuccessful }?.body()
            ?.let { entitlementMapper.toEntitlementEntity(it) }
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
