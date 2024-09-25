package com.valorant.store.api.val_api.skins.dto.skins

import java.util.UUID

data class SkinsBatchWrapperDTO(
    val status: Int,
    val data: List<SkinDTO>
)

data class SkinWrapperDTO(
    val status: Int,
    val data: SkinDTO
)

data class SkinDTO(
    val uuid: UUID,
    val displayName: String,
    val themeUuid: UUID,
    val contentTierUuid: String?,
    val displayIcon: String?,
    val wallpaper: String?,
    val assetPath: String,
    val chromas: List<SkinChromaDTO>,
    val levels: List<SkinLevelDTO>
)
