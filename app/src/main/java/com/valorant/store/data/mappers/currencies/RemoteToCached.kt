package com.valorant.store.data.mappers.currencies

import com.valorant.store.data.model.currencies.local.CurrencyBatchCached
import com.valorant.store.data.model.currencies.local.CurrencyCached
import com.valorant.store.data.model.currencies.remote.CurrencyBatchRemote

fun CurrencyBatchRemote.toCached(): CurrencyBatchCached = map {
    with(it) {
        CurrencyCached(
            uuid = uuid,
            displayName = displayName,
            displayNameSingular = displayNameSingular,
            displayIcon = displayIcon,
            largeIcon = largeIcon,
            assetPath = assetPath
        )
    }
}
