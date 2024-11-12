package com.valorant.store.data.datasource.entitlement.remote

import com.valorant.store.data.datasource.RemoteDatasource
import com.valorant.store.data.datasource.entitlement.service.EntitlementClient
import com.valorant.store.data.model.entitlement.EntitlementRemote
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EntitlementRemoteDatasource @Inject constructor(
    private val entitlementClient: EntitlementClient
) : RemoteDatasource {
    suspend fun getEntitlement(): EntitlementRemote? = entitlementClient.entitlement().getOrNull()
}
