package com.valorant.store.api.val_api.skins.dto.currencies

import com.valorant.store.api.val_api.skins.dto.BaseBatchWrapperDTO
import java.util.UUID

data class CurrencyBatchWrapperDTO(
    override val status: Int,
    override val data: List<CurrencyDTO>
) : BaseBatchWrapperDTO

data class CurrencyDTO(
    val uuid: UUID,
    val displayName: String,
    val displayNameSingular: String,
    val displayIcon: String,
    val largeIcon: String,
    val assetPath: String
)
