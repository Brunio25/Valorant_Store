package com.valorant.store.api.riot.store

import com.valorant.store.api.Api
import com.valorant.store.api.riot.store.dto.StorefrontDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path
import java.util.UUID

interface StoreApi : Api {
    @GET("/store/v2/storefront/{puuid}")
    suspend fun storefront(
        @Path("puuid") puuid: UUID,
        @HeaderMap headers: Map<String, String>
    ): Response<StorefrontDTO>
}