package com.valorant.store.domain.model.contentTiers

import java.util.UUID

typealias ContentTierMap = Map<UUID, ContentTier>

data class ContentTier(
    val displayName: String,
    val devName: String,
    val rank: Int,
    val highlightColor: String,
    val displayIcon: String
)
