package com.valorant.store.domain.mapper.valInfo

import com.valorant.store.app.model.valInfo.SkinChromaUi
import com.valorant.store.app.model.valInfo.SkinLevelUi
import com.valorant.store.app.model.valInfo.SkinUi
import com.valorant.store.domain.model.skins.Skin
import com.valorant.store.domain.model.skins.SkinChroma
import com.valorant.store.domain.model.skins.SkinLevel

fun Skin.toUi(): SkinUi = SkinUi(
    displayName = displayName,
    themeUuid = themeUuid,
    contentTierUuid = contentTierUuid,
    displayIcon = displayIcon,
    wallpaper = wallpaper,
    chromas = chromas.map { it.toUi() },
    levels = levels.map { it.toUi() }
)

private fun SkinChroma.toUi(): SkinChromaUi = SkinChromaUi(
    displayName = displayName,
    displayIcon = displayIcon,
    fullRender = fullRender,
    swatch = swatch,
    streamedVideo = streamedVideo
)

private fun SkinLevel.toUi(): SkinLevelUi = SkinLevelUi(
    displayName = displayName,
    levelItem = levelItem,
    displayIcon = displayIcon,
    streamedVideo = streamedVideo
)
