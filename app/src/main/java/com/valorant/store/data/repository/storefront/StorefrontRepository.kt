package com.valorant.store.data.repository.storefront

import com.valorant.store.data.datasource.storefront.remote.StorefrontRemoteDatasource
import com.valorant.store.data.mappers.storefront.toDomain
import com.valorant.store.domain.model.storefront.Storefront
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorefrontRepository @Inject constructor(
    private val storefrontRemoteDatasource: StorefrontRemoteDatasource
) {
    suspend fun getStorefront(userId: UUID, headers: Map<String, String>): Storefront? =
        storefrontRemoteDatasource.getStorefront(userId, headers)?.toDomain()
}
