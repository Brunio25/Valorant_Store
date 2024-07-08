package com.valorant.store.api.essential_data.entitlement

import com.valorant.store.api.Api
import com.valorant.store.api.essential_data.entitlement.dto.EntitlementDTO
import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.POST

interface EntitlementApi : Api {
    @POST("/api/token/v1")
    @Headers("Content-Type: application/json")
    suspend fun entitlement(): Response<EntitlementDTO>
}
