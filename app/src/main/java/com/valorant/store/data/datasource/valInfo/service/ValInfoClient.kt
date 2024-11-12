package com.valorant.store.data.datasource.valInfo.service

import com.valorant.store.data.datasource.Client
import com.valorant.store.data.model.clientVersion.remote.ClientVersionWrapperRemote
import com.valorant.store.data.model.contentTiers.remote.ContentTiersBatchWrapperRemote
import com.valorant.store.data.model.currencies.remote.CurrencyBatchWrapperRemote
import com.valorant.store.data.model.skins.remote.SkinsBatchWrapperRemote
import retrofit2.Response
import retrofit2.http.GET

interface ValInfoClient : Client {

    @GET("/v1/version")
    suspend fun clientVersion(): Response<ClientVersionWrapperRemote>

    @GET("/v1/weapons/skins")
    suspend fun skins(): Response<SkinsBatchWrapperRemote>

    @GET("/v1/currencies")
    suspend fun currencies(): Response<CurrencyBatchWrapperRemote>

    @GET("/v1/contenttiers")
    suspend fun contentTiers(): Response<ContentTiersBatchWrapperRemote>
}
