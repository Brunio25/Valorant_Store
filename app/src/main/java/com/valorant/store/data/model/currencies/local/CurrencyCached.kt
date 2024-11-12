package com.valorant.store.data.model.currencies.local

import java.util.UUID

typealias CurrencyBatchCached = List<CurrencyCached>

data class CurrencyCached(
    val uuid: UUID,
    val displayName: String,
    val displayNameSingular: String,
    val displayIcon: String,
    val largeIcon: String,
    val assetPath: String
)
