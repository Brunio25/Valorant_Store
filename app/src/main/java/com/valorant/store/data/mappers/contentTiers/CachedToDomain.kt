package com.valorant.store.data.mappers.contentTiers

import com.valorant.store.data.model.contentTiers.local.ContentTierBatchCached
import com.valorant.store.data.model.contentTiers.local.ContentTierCached
import com.valorant.store.domain.model.contentTiers.ContentTier
import com.valorant.store.domain.model.contentTiers.ContentTierMap

fun ContentTierBatchCached.toDomain(): ContentTierMap =
    associate { it.uuid to it.toDomain() }

private fun ContentTierCached.toDomain(): ContentTier = ContentTier(
    displayName = displayName,
    devName = devName,
    rank = rank,
    highlightColor = highlightColor,
    displayIcon = displayIcon
)
