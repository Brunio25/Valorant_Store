package com.valorant.store.api.val_api.skins.dto.content_tiers

import java.util.UUID

data class ContentTiersBatchWrapperDTO(
    val status: Int,
    val data: List<ContentTiersDTO>
)

data class ContentTiersDTO(
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
