package com.valorant.store.api.val_api.client_version

import com.valorant.store.api.Api
import com.valorant.store.api.val_api.client_version.dto.ClientVersionDto
import retrofit2.Response
import retrofit2.http.GET

interface ClientVersionApi : Api {
    @GET("/v1/version")
    suspend fun clientVersion(): Response<ClientVersionDto>
}