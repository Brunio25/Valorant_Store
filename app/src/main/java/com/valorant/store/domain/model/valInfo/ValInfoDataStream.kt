package com.valorant.store.domain.model.valInfo

import com.valorant.store.domain.model.contentTiers.ContentTierMap
import com.valorant.store.domain.model.currencies.CurrencyMap
import com.valorant.store.domain.model.skins.SkinMap

data class ValInfoDataStream(
    val contentTiers: ContentTierMap?,
    val currencies: CurrencyMap?,
    val skins: SkinMap?
)
