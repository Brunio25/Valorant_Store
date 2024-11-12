package com.valorant.store.data.mappers.contentTiers

import com.valorant.store.data.model.contentTiers.local.ContentTierBatchCached
import com.valorant.store.data.model.contentTiers.local.ContentTierCached
import com.valorant.store.data.model.contentTiers.remote.ContentTierBatchRemote

fun ContentTierBatchRemote.toCached(): ContentTierBatchCached = map {
    with(it) {
        ContentTierCached(
            uuid = uuid,
            displayName = displayName,
            devName = devName,
            rank = rank,
            juiceValue = juiceValue,
            juiceCost = juiceCost,
            highlightColor = highlightColor,
            displayIcon = displayIcon,
            assetPath = assetPath
        )
    }
}
