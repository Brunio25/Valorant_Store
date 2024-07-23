package com.valorant.store.api.riot.entitlement.dto

import com.google.gson.annotations.SerializedName

data class EntitlementDTO(
    @SerializedName("entitlements_token")
    val entitlementsToken: String
)
