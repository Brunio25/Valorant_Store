package com.valorant.store.data.mappers.skins

import com.valorant.store.data.model.skins.remote.SkinBatchRemote
import com.valorant.store.data.model.skins.remote.SkinChromaRemote
import com.valorant.store.data.model.skins.remote.SkinLevelRemote
import com.valorant.store.data.model.skins.remote.SkinRemote
import com.valorant.store.domain.model.skins.Skin
import com.valorant.store.domain.model.skins.SkinChroma
import com.valorant.store.domain.model.skins.SkinLevel
import com.valorant.store.domain.model.skins.SkinMap

fun SkinBatchRemote.toDomain(): SkinMap =
    associate { it.levels.first().uuid to it.toDomain() }

private fun SkinRemote.toDomain(): Skin = Skin(
    uuid = uuid,
    displayName = displayName,
    themeUuid = themeUuid,
    contentTierUuid = contentTierUuid,
    displayIcon = displayIcon,
    wallpaper = wallpaper,
    assetPath = assetPath,
    chromas = chromas.map { it.toDomain() },
    levels = levels.map { it.toDomain() }
)

private fun SkinChromaRemote.toDomain(): SkinChroma = SkinChroma(
    uuid = uuid,
    displayName = displayName,
    displayIcon = displayIcon,
    fullRender = fullRender,
    swatch = swatch,
    streamedVideo = streamedVideo,
    assetPath = assetPath
)

private fun SkinLevelRemote.toDomain(): SkinLevel = SkinLevel(
    uuid = uuid,
    displayName = displayName,
    levelItem = levelItem,
    displayIcon = displayIcon,
    streamedVideo = streamedVideo,
    assetPath = assetPath
)
