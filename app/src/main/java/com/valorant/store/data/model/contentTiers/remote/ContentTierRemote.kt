package com.valorant.store.data.model.contentTiers.remote

import com.valorant.store.data.model.WrapperRemote
import java.util.UUID

data class ContentTiersBatchWrapperRemote(
    override val status: Int,
    override val data: ContentTierBatchRemote
) : WrapperRemote<ContentTierBatchRemote>

typealias ContentTierBatchRemote = List<ContentTierRemote>

data class ContentTierRemote(
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
