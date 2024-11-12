package com.valorant.store.data.model.skins.remote

import com.valorant.store.data.model.WrapperRemote
import java.util.UUID

data class SkinsBatchWrapperRemote(
    override val status: Int,
    override val data: SkinBatchRemote
) : WrapperRemote<SkinBatchRemote>

typealias SkinBatchRemote = List<SkinRemote>

data class SkinRemote(
    val uuid: UUID,
    val displayName: String,
    val themeUuid: UUID,
    val contentTierUuid: String?,
    val displayIcon: String?,
    val wallpaper: String?,
    val assetPath: String,
    val chromas: List<SkinChromaRemote>,
    val levels: List<SkinLevelRemote>
)

data class SkinChromaRemote(
    val uuid: String,
    val displayName: String,
    val displayIcon: String?,
    val fullRender: String,
    val swatch: String?,
    val streamedVideo: String?,
    val assetPath: String
)

data class SkinLevelRemote(
    val uuid: UUID,
    val displayName: String,
    val levelItem: String?,
    val displayIcon: String?,
    val streamedVideo: String?,
    val assetPath: String
)
