package com.valorant.store.data.mappers.entitlement

import com.valorant.store.data.model.entitlement.EntitlementRemote
import com.valorant.store.domain.model.entitlement.Entitlement

fun EntitlementRemote.toDomain(): Entitlement = Entitlement(entitlementsToken)
