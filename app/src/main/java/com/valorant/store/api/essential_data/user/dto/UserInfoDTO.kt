package com.valorant.store.api.essential_data.user.dto

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.util.UUID

data class UserInfoDTO(
    val country: String,
    val sub: UUID,
    @SerializedName("email_verified")
    val emailVerified: Boolean,
    @SerializedName("player_plocale")
    val playerPlocale: String?,
    @SerializedName("country_at")
    val countryAt: LocalDateTime,
    val pw: UserInfoPwDTO,
    @SerializedName("phone_number_verified")
    val phoneNumberVerified: Boolean,
    @SerializedName("account_verified")
    val accountVerified: Boolean,
    val ppid: String?,
    @SerializedName("federated_identity_details")
    val federatedIdentityDetails: List<FederatedIdentityDetail>,
    @SerializedName("federated_identity_providers")
    val federatedIdentityProviders: List<String>,
    @SerializedName("player_locale")
    val playerLocale: String?,
    val acct: UserInfoAccountDTO,
    val age: Int,
    val jti: String,
    val affinity: Map<String, String>,
);

data class UserInfoPwDTO(
    @SerializedName("cng_at")
    val cngAt: LocalDateTime,
    val reset: Boolean,
    @SerializedName("must_reset")
    val mustReset: Boolean
)

data class UserInfoAccountDTO(
    val type: Int,
    val state: String,
    val adm: Boolean,
    @SerializedName("game_name")
    val gameName: String,
    @SerializedName("tag_line")
    val tagLine: String,
    @SerializedName("created_at")
    val createdAt: LocalDateTime,
)

data class FederatedIdentityDetail(
    @SerializedName("provider_name")
    val providerName: String,
    @SerializedName("provider_environment")
    val providerEnvironment: String
)
