package com.valorant.store.data.model.entitlement

import com.fasterxml.jackson.annotation.JsonProperty

data class EntitlementRemote(
    @JsonProperty("entitlements_token")
    val entitlementsToken: String
)
