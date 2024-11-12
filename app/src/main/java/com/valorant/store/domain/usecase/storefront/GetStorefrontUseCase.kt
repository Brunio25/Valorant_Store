package com.valorant.store.domain.usecase.storefront

import com.valorant.store.data.config.interceptors.StorefrontUrlInterceptor
import com.valorant.store.data.datasource.storefront.remote.StoreHeaders
import com.valorant.store.data.repository.clientPlatform.ClientPlatformRepository
import com.valorant.store.data.repository.clientVersion.ClientVersionRepository
import com.valorant.store.data.repository.entitlement.EntitlementRepository
import com.valorant.store.data.repository.storefront.StorefrontRepository
import com.valorant.store.data.repository.user.UserRepository
import com.valorant.store.domain.mapper.storefront.toUi
import com.valorant.store.domain.model.clientPlatform.ClientPlatform
import com.valorant.store.domain.model.clientVersion.ClientVersion
import com.valorant.store.domain.model.entitlement.Entitlement
import com.valorant.store.domain.model.storefront.Storefront
import com.valorant.store.domain.model.user.User
import com.valorant.store.domain.usecase.valInfo.GetValInfoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetStorefrontUseCase @Inject constructor(
    private val clientVersionRepository: ClientVersionRepository,
    private val userRepository: UserRepository,
    private val entitlementRepository: EntitlementRepository,
    private val clientPlatformRepository: ClientPlatformRepository,
    private val storefrontRepository: StorefrontRepository,
    private val getValInfoUseCase: GetValInfoUseCase
) {
    companion object {
        private data class StorefrontEssentialData(
            val user: User,
            val entitlement: Entitlement,
            val clientVersion: ClientVersion,
            val clientPlatform: ClientPlatform
        )
    }

    suspend operator fun invoke() = combine(
        getStorefront(),
        getValInfoUseCase()
    ) { storefront, valInfo ->
        storefront to valInfo
    }.map { (storefront, valInfo) ->
        if (storefront == null || valInfo == null) return@map null

        storefront.toUi(valInfo)
    }

    private suspend fun getStorefront(): Flow<Storefront?> {
        val requiredData = getRequiredData() ?: return flowOf(null)

        val shard = requiredData.user.shard ?: return flowOf(null)
        StorefrontUrlInterceptor.setShardProvider { shard }

        return flow {
            emit(
                storefrontRepository.getStorefront(
                    userId = requiredData.user.id,
                    headers = requiredData.toHeadersMap()
                )
            )
        }
    }

    private suspend fun getRequiredData(): StorefrontEssentialData? = withContext(Dispatchers.IO) {
        val clientVersionDeferred =
            async { clientVersionRepository.getClientVersion() }
        val userDeferred = async { userRepository.getUser() }
        val entitlementDeferred = async { entitlementRepository.getEntitlement() }

        val clientVersion = clientVersionDeferred.await() ?: return@withContext null
        val user = userDeferred.await() ?: return@withContext null
        val entitlement = entitlementDeferred.await() ?: return@withContext null
        val clientPlatform = clientPlatformRepository.getClientPlatform()

        return@withContext StorefrontEssentialData(
            user = user,
            entitlement = entitlement,
            clientVersion = clientVersion,
            clientPlatform = clientPlatform
        )
    }

    private fun StorefrontEssentialData.toHeadersMap(): Map<String, String> = mapOf(
        StoreHeaders.ENTITLEMENT to entitlement.token,
        StoreHeaders.CLIENT_VERSION to clientVersion.riotVersion,
        StoreHeaders.CLIENT_PLATFORM to clientPlatform.encodedClientPlatform
    ).mapKeys { it.key.value }
}
