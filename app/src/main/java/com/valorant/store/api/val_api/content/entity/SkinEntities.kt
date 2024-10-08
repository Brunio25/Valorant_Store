package com.valorant.store.api.val_api.content.entity

import com.valorant.store.api.val_api.content.dto.skins.SkinChromaDTO
import com.valorant.store.api.val_api.content.dto.skins.SkinLevelDTO
import java.util.UUID

typealias SkinMapEntity = Map<UUID, SkinEntity>

data class SkinBatchEntity(
    val skins: List<SkinEntity>
)

data class SkinEntity(
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
    val displayIcon: String?,
    val streamedVideo: String?,
    val assetPath: String
)
