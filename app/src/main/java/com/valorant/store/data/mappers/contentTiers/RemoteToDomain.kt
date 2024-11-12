package com.valorant.store.data.mappers.contentTiers

import com.valorant.store.data.model.contentTiers.remote.ContentTierBatchRemote
import com.valorant.store.data.model.contentTiers.remote.ContentTierRemote
import com.valorant.store.domain.model.contentTiers.ContentTier
import com.valorant.store.domain.model.contentTiers.ContentTierMap

fun ContentTierBatchRemote.toDomain(): ContentTierMap =
    associate { it.uuid to it.toDomain() }

private fun ContentTierRemote.toDomain(): ContentTier = ContentTier(
    displayName = displayName,
    devName = devName,
    rank = rank,
    highlightColor = highlightColor,
    displayIcon = displayIcon
)
