package com.valorant.store.api.val_api.skins.dto.skins

data class SkinChromasBatchWrapperDTO(
    val status: Int,
    val data: List<SkinChromaDTO>
)

data class SkinChromasWrapperDTO(
    val status: Int,
    val data: SkinChromaDTO
)

data class SkinChromaDTO(
    val uuid: String,
    val displayName: String,
    val displayIcon: String?,
    val fullRender: String,
    val swatch: String?,
    val streamedVideo: String?,
    val assetPath: String
)
