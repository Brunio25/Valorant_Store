package com.valorant.store.api.store

import com.valorant.store.api.Api
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path
import java.util.UUID

interface StoreApi : Api {
    @GET("/store/v2/storefront/{puuid}")
    fun storefront(@Path("puuid") puuid: UUID, @HeaderMap headers: Map<String, String>)
}