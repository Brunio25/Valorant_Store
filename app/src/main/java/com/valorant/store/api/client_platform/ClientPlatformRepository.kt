package com.valorant.store.api.client_platform

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.valorant.store.api.client_platform.dto.ClientPlatformDTO
import com.valorant.store.extensions.writeValueAsBase64

object ClientPlatformRepository {
    private val mapper = ObjectMapper().registerKotlinModule()
    private val clientPlatform = ClientPlatformDTO()

    fun getClientPlatform() = mapper.writeValueAsBase64(clientPlatform)
        .let { ClientPlatformEntity.of(it) }
}

data class ClientPlatformEntity(val encodedClientPlatform: String) {
    companion object {
        fun of(encodedClientPlatform: String) = ClientPlatformEntity(encodedClientPlatform)
    }
}
