package com.valorant.store.api.val_api.skins.dto.currencies

import java.util.UUID

data class CurrencyBatchWrapperDTO(
    val status: Int,
    val data: List<CurrencyDTO>
)

data class CurrencyDTO(
    val uuid: UUID,
    val displayName: String,
    val displayNameSingular: String,
    val displayIcon: String,
    val largeIcon: String,
    val assetPath: String
)
