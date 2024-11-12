package com.valorant.store.data.model.skins.local

import java.util.UUID

typealias SkinBatchCached = List<SkinCached>

data class SkinCached(
    val uuid: UUID,
    val displayName: String,
    val themeUuid: UUID,
    val contentTierUuid: String?,
    val displayIcon: String?,
    val wallpaper: String?,
    val assetPath: String,
    val chromas: List<SkinChromaCached>,
    val levels: List<SkinLevelCached>
)

data class SkinChromaCached(
    val uuid: String,
    val displayName: String,
    val displayIcon: String?,
    val fullRender: String,
    val swatch: String?,
    val streamedVideo: String?,
    val assetPath: String
)

data class SkinLevelCached(
    val uuid: UUID,
    val displayName: String,
    val levelItem: String?,
    val displayIcon: String?,
    val streamedVideo: String?,
    val assetPath: String
)
