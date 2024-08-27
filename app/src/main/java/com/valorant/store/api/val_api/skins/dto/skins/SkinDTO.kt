package com.valorant.store.api.val_api.skins.dto.skins

import com.valorant.store.api.val_api.skins.dto.BaseBatchWrapperDTO

data class SkinsBatchWrapperDTO(
    override val status: Int,
    override val data: List<SkinDTO>
) : BaseBatchWrapperDTO

data class SkinWrapperDTO(
    val status: Int,
    val data: SkinDTO
)

data class SkinDTO(
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
