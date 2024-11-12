package com.valorant.store.domain.model.skins

import java.util.UUID

typealias SkinMap = Map<UUID, Skin>

data class SkinBatch(
    val skins: List<Skin>
)

data class Skin(
    val uuid: UUID,
    val displayName: String,
    val themeUuid: UUID,
    val contentTierUuid: String?,
    val displayIcon: String?,
    val wallpaper: String?,
    val assetPath: String,
    val chromas: List<SkinChroma>,
    val levels: List<SkinLevel>
)

data class SkinChroma(
    val uuid: String,
    val displayName: String,
    val displayIcon: String?,
    val fullRender: String,
    val swatch: String?,
    val streamedVideo: String?,
    val assetPath: String
)

data class SkinLevel(
    val uuid: UUID,
    val displayName: String?,
    val levelItem: String?,
    val displayIcon: String?,
    val streamedVideo: String?,
    val assetPath: String
)
