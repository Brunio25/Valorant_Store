package com.valorant.store.data.datasource.storefront.remote

import com.valorant.store.data.datasource.RemoteDatasource
import com.valorant.store.data.datasource.storefront.service.StorefrontClient
import com.valorant.store.data.model.storefront.StorefrontRemote
import java.util.UUID
import javax.inject.Inject

class StorefrontRemoteDatasource @Inject constructor(
    private val storefrontClient: StorefrontClient
) : RemoteDatasource {

    suspend fun getStorefront(
        userId: UUID,
        headersMap: Map<String, String>
    ): StorefrontRemote? = storefrontClient.storefront(userId, headersMap).getOrNull()
}

enum class StoreHeaders(val value: String) {
    CLIENT_PLATFORM("X-Riot-ClientPlatform"),
    CLIENT_VERSION("X-Riot-ClientVersion"),
    ENTITLEMENT("X-Riot-Entitlements-JWT");
}
