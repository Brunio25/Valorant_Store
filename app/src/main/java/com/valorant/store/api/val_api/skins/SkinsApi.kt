package com.valorant.store.api.val_api.skins

import com.valorant.store.api.Api
import com.valorant.store.api.val_api.skins.dto.content_tiers.ContentTiersWrapperDTO
import com.valorant.store.api.val_api.skins.dto.currencies.CurrencyWrapperDTO
import com.valorant.store.api.val_api.skins.dto.skins.SkinsBatchWrapperDTO
import retrofit2.Response
import retrofit2.http.GET

interface SkinsApi : Api {
    @GET("/v1/weapons/skins")
    suspend fun skins(): Response<SkinsBatchWrapperDTO>

    @GET("/v1/currencies")
    suspend fun currencies(): Response<CurrencyWrapperDTO>

    @GET("/v1/contenttiers")
    suspend fun contentTiers(): Response<ContentTiersWrapperDTO>
}
