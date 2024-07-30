package com.valorant.store.api.val_api.skins.dto

data class SkinsBatchWrapperDTO(
    val status: Int,
    val data: List<SkinDTO>
)

data class SkinWrapperDTO(
    val status: Int,
    val data: SkinDTO
)

data class SkinDTO(
    val uuid: String,
    val displayName: String,
    val themeUuid: String,
    val contentTierUuid: String,
    val displayIcon: String,
    val wallpaper: String?,
    val assetPath: String,
    val chromas: List<SkinChromaDTO>,
    val levels: List<SkinLevelDTO>
)
