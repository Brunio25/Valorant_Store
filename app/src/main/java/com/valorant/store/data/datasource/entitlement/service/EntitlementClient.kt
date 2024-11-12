package com.valorant.store.data.datasource.entitlement.service

import com.valorant.store.data.datasource.Client
import com.valorant.store.data.model.entitlement.EntitlementRemote
import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.POST

interface EntitlementClient : Client {
    @POST("/api/token/v1")
    @Headers("Content-Type: application/json")
    suspend fun entitlement(): Response<EntitlementRemote>
}
