package com.valorant.store.api.essential_data.entitlement.dto

import com.google.gson.annotations.SerializedName

data class EntitlementDTO(
    @SerializedName("entitlements_token")
    val entitlementsToken: String
)
