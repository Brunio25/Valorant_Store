package com.valorant.store.data.mappers.clientVersion

import com.valorant.store.data.model.clientVersion.local.ClientVersionCached
import com.valorant.store.data.model.clientVersion.remote.ClientVersionRemote

fun ClientVersionRemote.toCached(): ClientVersionCached = ClientVersionCached(
    manifestId = manifestId,
    branch = branch,
    version = version,
    buildVersion = buildVersion,
    engineVersion = engineVersion,
    riotClientVersion = riotClientVersion,
    riotClientBuild = riotClientBuild,
    buildDate = buildDate
)
