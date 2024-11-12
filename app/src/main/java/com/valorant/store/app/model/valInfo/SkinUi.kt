package com.valorant.store.app.model.valInfo

import java.util.UUID

data class SkinUi(
    val displayName: String,
    val themeUuid: UUID,
    val contentTierUuid: String?,
    val displayIcon: String?,
    val wallpaper: String?,
    val chromas: List<SkinChromaUi>,
    val levels: List<SkinLevelUi>
)

data class SkinChromaUi(
    val displayName: String,
    val displayIcon: String?,
    val fullRender: String,
    val swatch: String?,
    val streamedVideo: String?,
)

data class SkinLevelUi(
    val displayName: String?,
    val levelItem: String?,
    val displayIcon: String?,
    val streamedVideo: String?,
)
