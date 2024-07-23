package com.valorant.store.api.client_platform.dto

data class ClientPlatformDTO(
    val platformType: String = "PC",
    val platformOS: String = "Windows",
    val platformOSVersion: String = "10.0.19042.1.256.64bit",
    val platformChipset: String = "Unknown"
)
