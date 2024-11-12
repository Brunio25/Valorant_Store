package com.valorant.store.data.datasource.storefront.service

import com.valorant.store.data.datasource.Client
import com.valorant.store.data.model.storefront.StorefrontRemote
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface StorefrontClient : Client {
    @POST("/store/v3/storefront/{puuid}")
    suspend fun storefront(
        @Path("puuid") userId: UUID,
        @HeaderMap headers: Map<String, String>,
        @Body emptyBody: Map<String, String> = emptyMap()
    ): Response<StorefrontRemote>
}
