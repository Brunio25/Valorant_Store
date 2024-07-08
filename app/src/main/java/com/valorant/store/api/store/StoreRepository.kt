package com.valorant.store.api.store

import com.valorant.store.api.Repository
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

    suspend fun getStorefront() {
        val response = apiClient.storefront(UUID.randomUUID(), emptyMap())
    }
}