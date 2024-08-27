package com.valorant.store.api.riot.user.dto

import java.time.LocalDateTime
import java.util.UUID
import com.fasterxml.jackson.annotation.JsonProperty

data class UserInfoDTO(
    val country: String,
    val sub: UUID,
    @JsonProperty("email_verified")
    val emailVerified: Boolean,
    @JsonProperty("player_plocale")
    val playerPlocale: String?,
    @JsonProperty("country_at")
    val countryAt: LocalDateTime,
    val pw: UserInfoPwDTO,
    @JsonProperty("phone_number_verified")
    val phoneNumberVerified: Boolean,
    @JsonProperty("account_verified")
    val accountVerified: Boolean,
    val ppid: String?,
    @JsonProperty("federated_identity_details")
    val federatedIdentityDetails: List<FederatedIdentityDetail>,
    @JsonProperty("federated_identity_providers")
    val federatedIdentityProviders: List<String>,
    @JsonProperty("player_locale")
    val playerLocale: String?,
    val acct: UserInfoAccountDTO,
    val age: Int,
    val jti: String,
    val affinity: Map<String, String>,
)

data class UserInfoPwDTO(
    @JsonProperty("cng_at")
    val cngAt: LocalDateTime,
    val reset: Boolean,
    @JsonProperty("must_reset")
    val mustReset: Boolean
)

data class UserInfoAccountDTO(
    val type: Int,
    val state: String,
    val adm: Boolean,
    @JsonProperty("game_name")
    val gameName: String,
    @JsonProperty("tag_line")
    val tagLine: String,
    @JsonProperty("created_at")
    val createdAt: LocalDateTime,
)

data class FederatedIdentityDetail(
    @JsonProperty("provider_name")
    val providerName: String,
    @JsonProperty("provider_environment")
    val providerEnvironment: String?
)
