package com.valorant.store.api.val_api.skins.dto.skins

import android.net.Uri
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
    val displayIcon: Uri?,
    val streamedVideo: Uri?,
    val assetPath: String
)
