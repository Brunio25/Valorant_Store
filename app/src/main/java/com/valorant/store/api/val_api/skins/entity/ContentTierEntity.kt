package com.valorant.store.api.val_api.skins.entity

import java.util.UUID

typealias ContentTierMapEntity = Map<UUID, ContentTierEntity>

data class ContentTierEntity(
    val displayName: String,
    val devName: String,
    val rank: Int,
    val highlightColor: String,
    val displayIcon: String
)
