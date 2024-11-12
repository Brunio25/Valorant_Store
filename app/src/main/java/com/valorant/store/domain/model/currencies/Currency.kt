package com.valorant.store.domain.model.currencies

import java.util.UUID

typealias CurrencyMap = Map<UUID, Currency>

data class Currency(
    val displayName: String,
    val displayNameSingular: String,
    val displayIcon: String,
    val largeIcon: String,
)
