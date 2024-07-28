package com.valorant.store.api.state_control.riot_store.entity

import com.valorant.store.api.client_platform.ClientPlatformEntity
import com.valorant.store.api.riot.entitlement.EntitlementEntity
import com.valorant.store.api.riot.store.StoreHeaders
import com.valorant.store.api.riot.user.UserEntity
import com.valorant.store.api.val_api.client_version.ClientVersionEntity

class RiotStoreEssentialDataEntity private constructor(
    val user: UserEntity,
    private val entitlement: EntitlementEntity,
    private val clientVersion: ClientVersionEntity,
    private val clientPlatform: ClientPlatformEntity
) {
    companion object {
        fun of(
            user: Result<UserEntity>,
            entitlement: Result<EntitlementEntity>,
            clientVersion: Result<ClientVersionEntity>,
            clientPlatform: ClientPlatformEntity
        ): Result<RiotStoreEssentialDataEntity> {
            val userSuccess = user.getOrElse { return Result.failure(it) }
            val entitlementSuccess = entitlement.getOrElse { return Result.failure(it) }
            val clientVersionSuccess = clientVersion.getOrElse { return Result.failure(it) }

            return RiotStoreEssentialDataEntity(
                userSuccess,
                entitlementSuccess,
                clientVersionSuccess,
                clientPlatform
            ).let { Result.success(it) }
        }
    }

    fun toHeadersMap(): Map<String, String> = mapOf(
        StoreHeaders.ENTITLEMENT to entitlement.entitlementToken,
        StoreHeaders.CLIENT_VERSION to clientVersion.version,
        StoreHeaders.CLIENT_PLATFORM to clientPlatform.encodedClientPlatform
    ).mapKeys { it.key.value }
}
