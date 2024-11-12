package com.valorant.store.data.model.currencies.remote

import com.valorant.store.data.model.WrapperRemote
import java.util.UUID

data class CurrencyBatchWrapperRemote(
    override val status: Int,
    override val data: CurrencyBatchRemote
) : WrapperRemote<CurrencyBatchRemote>

typealias CurrencyBatchRemote = List<CurrencyRemote>

data class CurrencyRemote(
    val uuid: UUID,
    val displayName: String,
    val displayNameSingular: String,
    val displayIcon: String,
    val largeIcon: String,
    val rewardPreviewIcon: String,
    val assetPath: String
)
