package com.valorant.store.api.val_api.skins.entity

import android.net.Uri
import com.valorant.store.api.val_api.skins.dto.SkinChromaDTO
import com.valorant.store.api.val_api.skins.dto.SkinLevelDTO
import java.util.UUID

typealias SkinMapEntity = Map<UUID, SkinEntity>

data class SkinBatchEntity(
    val skins: List<SkinEntity>
)

data class SkinEntity(
    val uuid: String,
    val displayName: String,
    val themeUuid: String,
    val contentTierUuid: String?,
    val displayIcon: String?,
    val wallpaper: String?,
    val assetPath: String,
    val chromas: List<SkinChromaDTO>,
    val levels: List<SkinLevelDTO>
)

data class SkinChromaEntity(
    val uuid: String,
    val displayName: String,
    val displayIcon: String?,
    val fullRender: String,
    val swatch: String?,
    val streamedVideo: String?,
    val assetPath: String
)

data class SkinLevelEntity(
    val uuid: UUID,
    val displayName: String?,
    val levelItem: String?,
    val displayIcon: Uri?,
    val streamedVideo: Uri?,
    val assetPath: String
)
