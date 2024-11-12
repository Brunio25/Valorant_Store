package com.valorant.store.data.mappers.clientVersion

import com.valorant.store.data.model.clientVersion.remote.ClientVersionRemote
import com.valorant.store.domain.model.clientVersion.ClientVersion

fun ClientVersionRemote.toDomain(): ClientVersion = ClientVersion(
    riotVersion = riotClientVersion,
    valApiVersion = branch
)
