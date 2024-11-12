package com.valorant.store.data.model.clientPlatform

data class ClientPlatformLocal(
    val platformType: String = "PC",
    val platformOS: String = "Windows",
    val platformOSVersion: String = "10.0.19042.1.256.64bit",
    val platformChipset: String = "Unknown"
)
