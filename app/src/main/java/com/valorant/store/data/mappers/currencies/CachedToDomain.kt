package com.valorant.store.data.mappers.currencies

import com.valorant.store.data.model.currencies.local.CurrencyBatchCached
import com.valorant.store.data.model.currencies.local.CurrencyCached
import com.valorant.store.domain.model.currencies.Currency
import com.valorant.store.domain.model.currencies.CurrencyMap

fun CurrencyBatchCached.toDomain(): CurrencyMap =
    associate { it.uuid to it.toDomain() }

private fun CurrencyCached.toDomain(): Currency = Currency(
    displayName = displayName,
    displayNameSingular = displayNameSingular,
    displayIcon = displayIcon,
    largeIcon = largeIcon
)
