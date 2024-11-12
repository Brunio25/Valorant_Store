package com.valorant.store.data.repository.entitlement

import com.valorant.store.data.datasource.entitlement.remote.EntitlementRemoteDatasource
import com.valorant.store.data.mappers.entitlement.toDomain
import com.valorant.store.domain.model.entitlement.Entitlement
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EntitlementRepository @Inject constructor(
    private val entitlementRemoteDatasource: EntitlementRemoteDatasource
) {
    suspend fun getEntitlement(): Entitlement? =
        entitlementRemoteDatasource.getEntitlement()?.toDomain()
}
