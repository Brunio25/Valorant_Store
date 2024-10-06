package com.valorant.store.api.val_api.content

import com.valorant.store.api.Api
import com.valorant.store.api.val_api.content.dto.content_tiers.ContentTiersBatchWrapperDTO
import com.valorant.store.api.val_api.content.dto.currencies.CurrencyBatchWrapperDTO
import com.valorant.store.api.val_api.content.dto.skins.SkinsBatchWrapperDTO
import retrofit2.Response
import retrofit2.http.GET

interface ValInfoApi : Api {
    @GET("/v1/weapons/skins")
    suspend fun skins(): Response<SkinsBatchWrapperDTO>

    @GET("/v1/currencies")
    suspend fun currencies(): Response<CurrencyBatchWrapperDTO>

    @GET("/v1/contenttiers")
    suspend fun contentTiers(): Response<ContentTiersBatchWrapperDTO>
}
