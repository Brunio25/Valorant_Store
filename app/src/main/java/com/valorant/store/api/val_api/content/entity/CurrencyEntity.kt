package com.valorant.store.api.val_api.content.entity

import java.util.UUID

typealias CurrencyMapEntity = Map<UUID, CurrencyEntity>

data class CurrencyEntity(
    val displayName: String,
    val displayNameSingular: String,
    val displayIcon: String,
    val largeIcon: String,
)
