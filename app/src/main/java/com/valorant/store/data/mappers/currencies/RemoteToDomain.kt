package com.valorant.store.data.mappers.currencies

import com.valorant.store.data.model.currencies.remote.CurrencyBatchRemote
import com.valorant.store.data.model.currencies.remote.CurrencyRemote
import com.valorant.store.domain.model.currencies.Currency
import com.valorant.store.domain.model.currencies.CurrencyMap

fun CurrencyBatchRemote.toDomain(): CurrencyMap =
    associate { it.uuid to it.toDomain() }

private fun CurrencyRemote.toDomain(): Currency = Currency(
    displayName = displayName,
    displayNameSingular = displayNameSingular,
    displayIcon = displayIcon,
    largeIcon = largeIcon
)
