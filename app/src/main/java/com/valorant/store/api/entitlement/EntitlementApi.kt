package com.valorant.store.api.entitlement

import com.valorant.store.api.Api
import com.valorant.store.api.entitlement.dto.EntitlementDTO
import retrofit2.Response
import retrofit2.http.POST

interface EntitlementApi : Api {
    @POST("/api/token/v1")
    suspend fun entitlement(): Response<EntitlementDTO>
}
