package com.valorant.store.api.val_api.skins

import com.valorant.store.api.Api
import com.valorant.store.api.val_api.skins.dto.SkinLevelWrapperDTO
import com.valorant.store.api.val_api.skins.dto.SkinsBatchWrapperDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.UUID

interface SkinsApi : Api {
    @GET("/v1/weapons/skins")
    suspend fun skins(): Response<SkinsBatchWrapperDTO>

    @GET("/v1/weapons/skinlevels/{skinLevelId}")
    suspend fun skinLevel(@Path("skinLevelId") skinLevelId: UUID): Response<SkinLevelWrapperDTO>
}
