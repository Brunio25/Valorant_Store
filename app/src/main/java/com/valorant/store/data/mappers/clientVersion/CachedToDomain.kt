package com.valorant.store.data.mappers.clientVersion

import com.valorant.store.data.model.clientVersion.local.ClientVersionCached
import com.valorant.store.domain.model.clientVersion.ClientVersion

fun ClientVersionCached.toDomain(): ClientVersion = ClientVersion(
    riotVersion = riotClientVersion,
    valApiVersion = branch
)
