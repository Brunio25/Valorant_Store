package com.valorant.store.api.store

import com.valorant.store.api.Repository
import com.valorant.store.api.store.dto.StorefrontDTO
import java.util.UUID
import kotlin.concurrent.Volatile

class StoreRepository private constructor(shard: String) :
    Repository<StoreApi>(StoreApi::class.java) {
    override val baseUrl: String = "https://pd.$shard.a.pvp.net"

    companion object {
        @Volatile
        private var INSTANCE: StoreRepository? = null

        fun getInstance(shard: String): StoreRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: StoreRepository(shard).also { INSTANCE = it }
            }
    }

    suspend fun getStorefront(puuid: UUID, headersMap: Map<String, String>): Result<StorefrontDTO> =
        try {
            val response = apiClient.storefront(puuid, headersMap)
            response.takeIf { it.isSuccessful }?.body()
                ?.let { Result.success(it) }
                ?: Result.failure(Exception("Null response body"))
        } catch (e: Exception) {
            Result.failure(e)
        }
}

enum class StoreHeaders(val value: String) {
    CLIENT_PLATFORM("X-Riot-ClientPlatform"),
    CLIENT_VERSION("X-Riot-ClientVersion"),
    ENTITLEMENT("X-Riot-Entitlements-JWT");
}
