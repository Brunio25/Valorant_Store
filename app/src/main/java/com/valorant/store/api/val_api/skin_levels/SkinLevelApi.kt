package com.valorant.store.api.val_api.skin_levels

import com.valorant.store.api.Api
import com.valorant.store.api.val_api.skin_levels.dto.SkinLevelDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.UUID

interface SkinLevelApi : Api {
    @GET("/{skinLevelId}")
    suspend fun skinLevel(@Path("skinLevelId") skinLevelId: UUID): Response<SkinLevelDTO>
}
