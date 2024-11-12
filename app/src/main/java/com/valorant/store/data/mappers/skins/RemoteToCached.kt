package com.valorant.store.data.mappers.skins

import com.valorant.store.data.model.skins.local.SkinBatchCached
import com.valorant.store.data.model.skins.local.SkinCached
import com.valorant.store.data.model.skins.local.SkinChromaCached
import com.valorant.store.data.model.skins.local.SkinLevelCached
import com.valorant.store.data.model.skins.remote.SkinBatchRemote
import com.valorant.store.data.model.skins.remote.SkinChromaRemote
import com.valorant.store.data.model.skins.remote.SkinLevelRemote
import com.valorant.store.data.model.skins.remote.SkinRemote

fun SkinBatchRemote.toCached(): SkinBatchCached = map { it.toCached() }

private fun SkinRemote.toCached(): SkinCached = SkinCached(
    uuid = uuid,
    displayName = displayName,
    themeUuid = themeUuid,
    contentTierUuid = contentTierUuid,
    displayIcon = displayIcon,
    wallpaper = wallpaper,
    assetPath = assetPath,
    chromas = chromas.map { it.toCached() },
    levels = levels.map { it.toCached() }
)

private fun SkinChromaRemote.toCached(): SkinChromaCached = SkinChromaCached(
    uuid = uuid,
    displayName = displayName,
    displayIcon = displayIcon,
    fullRender = fullRender,
    swatch = swatch,
    streamedVideo = streamedVideo,
    assetPath = assetPath
)

private fun SkinLevelRemote.toCached(): SkinLevelCached = SkinLevelCached(
    uuid = uuid,
    displayName = displayName,
    levelItem = levelItem,
    displayIcon = displayIcon,
    streamedVideo = streamedVideo,
    assetPath = assetPath
)
