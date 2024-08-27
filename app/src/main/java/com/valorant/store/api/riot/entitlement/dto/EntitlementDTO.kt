package com.valorant.store.api.riot.entitlement.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class EntitlementDTO(
    @JsonProperty("entitlements_token")
    val entitlementsToken: String
)
