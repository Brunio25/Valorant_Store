package com.valorant.store.api.client_platform

import android.util.Base64
import com.fasterxml.jackson.databind.ObjectMapper
import com.valorant.store.api.client_platform.dto.ClientPlatformDTO

object ClientPlatformRepository {
    private val mapper = ObjectMapper()
    private val clientPlatform = ClientPlatformDTO()

    fun getClientPlatform() = clientPlatform.toBase64().let { ClientPlatformEntity.of(it) }

    private fun ClientPlatformDTO.toBase64() = mapper.writeValueAsBytes(this)
        .let { Base64.encode(it, Base64.NO_WRAP) }
        .toString(Charsets.UTF_8)
}

data class ClientPlatformEntity(val encodedClientPlatform: String) {
    companion object {
        fun of(encodedClientPlatform: String) = ClientPlatformEntity(encodedClientPlatform)
    }
}
