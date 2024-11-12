package com.valorant.store.data.mappers.clientPlatform

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.valorant.store.data.extensions.writeValueAsBase64
import com.valorant.store.data.model.clientPlatform.ClientPlatformLocal
import com.valorant.store.domain.model.clientPlatform.ClientPlatform

private val mapper: ObjectMapper = ObjectMapper().registerKotlinModule()

fun ClientPlatformLocal.toDomain(): ClientPlatform = ClientPlatform(
    encodedClientPlatform = mapper.writeValueAsBase64(this)
)
