package com.valorant.store.api.val_api.content.dto.skins

import java.util.UUID

data class SkinLevelBatchWrapperDTO(
    val status: Int,
    val data: List<SkinLevelDTO>
)

data class SkinLevelWrapperDTO(
    val status: Int,
    val data: SkinLevelDTO
)

data class SkinLevelDTO(
    val uuid: UUID,
    val displayName: String,
    val levelItem: String?,
    val displayIcon: String?,
    val streamedVideo: String?,
    val assetPath: String
)
