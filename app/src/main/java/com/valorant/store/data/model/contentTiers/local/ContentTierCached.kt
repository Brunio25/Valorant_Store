package com.valorant.store.data.model.contentTiers.local

import java.util.UUID

typealias ContentTierBatchCached = List<ContentTierCached>

data class ContentTierCached(
    val uuid: UUID,
    val displayName: String,
    val devName: String,
    val rank: Int,
    val juiceValue: Int,
    val juiceCost: Int,
    val highlightColor: String,
    val displayIcon: String,
    val assetPath: String
)
