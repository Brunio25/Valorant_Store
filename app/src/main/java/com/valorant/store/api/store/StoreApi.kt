package com.valorant.store.api.store

import retrofit2.http.GET
import retrofit2.http.Path
import java.util.UUID

interface StoreApi {
    @GET("/store/v2/storefront/{puuid}")
    fun get(@Path("puuid") puuid: UUID)
}