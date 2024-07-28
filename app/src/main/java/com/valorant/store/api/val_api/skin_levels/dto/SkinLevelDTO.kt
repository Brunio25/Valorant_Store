package com.valorant.store.api.val_api.skin_levels.dto

import android.net.Uri
import java.util.UUID

data class SkinLevelDTO(
    val status: Int,
    val data: SkinLevelData
)

data class SkinLevelData(
    val uuid: UUID,
    val displayName: String,
    val levelItem: String?,
    val displayIcon: Uri?,
    val streamedVideo: Uri?,
    val assetPath: String
)
