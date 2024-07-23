package com.valorant.store.api.val_api.client_version.dto

data class ClientVersionDto(
    val status: String,
    val data: ClientVersionDataDto
)

data class ClientVersionDataDto(
    val manifestId: String,
    val branch: String,
    val version: String,
    val buildVersion: String,
    val engineVersion: String,
    val riotClientVersion: String,
    val riotClientBuild: String,
    val buildDate: String
)
