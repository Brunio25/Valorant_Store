package com.valorant.store.api.essential_data.client_platform

import android.util.Base64
import com.google.gson.GsonBuilder
import com.valorant.store.api.essential_data.client_platform.dto.ClientPlatformDTO

object ClientPlatformRepository {
    private val gson = GsonBuilder().setPrettyPrinting().create()
    private val clientPlatform = ClientPlatformDTO()

    fun getClientPlatform() = clientPlatform.toBase64().let { ClientPlatformEntity.of(it) }

    private fun ClientPlatformDTO.toBase64() = gson.toJson(this).encodeToByteArray()
        .let { Base64.encode(it, Base64.DEFAULT) }
        .toString(Charsets.UTF_8)
}

data class ClientPlatformEntity(val encodedClientPlatform: String) {
    companion object {
        fun of(encodedClientPlatform: String) = ClientPlatformEntity(encodedClientPlatform)
    }
}
