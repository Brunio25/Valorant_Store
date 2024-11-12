package com.valorant.store.data.datasource.valInfo.remote

import com.valorant.store.data.datasource.RemoteDatasource
import com.valorant.store.data.datasource.valInfo.service.ValInfoClient
import com.valorant.store.data.model.clientVersion.remote.ClientVersionRemote
import com.valorant.store.data.model.contentTiers.remote.ContentTierBatchRemote
import com.valorant.store.data.model.currencies.remote.CurrencyBatchRemote
import com.valorant.store.data.model.skins.remote.SkinBatchRemote
import javax.inject.Inject

class ValInfoRemoteDatasource @Inject constructor(
    private val valInfoClient: ValInfoClient
) : RemoteDatasource {
    suspend fun getClientVersion(): ClientVersionRemote? =
        valInfoClient.clientVersion().getOrNullWrapper()

    suspend fun getSkins(): SkinBatchRemote? =
        valInfoClient.skins().also { it.toString() }
            .getOrNullWrapper()

    suspend fun getContentTiers(): ContentTierBatchRemote? =
        valInfoClient.contentTiers().getOrNullWrapper()

    suspend fun getCurrencies(): CurrencyBatchRemote? =
        valInfoClient.currencies().getOrNullWrapper()
}
