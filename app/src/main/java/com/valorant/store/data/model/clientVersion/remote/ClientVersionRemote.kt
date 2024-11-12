package com.valorant.store.data.model.clientVersion.remote

import com.valorant.store.data.model.WrapperRemote

data class ClientVersionWrapperRemote(
    override val status: Int,
    override val data: ClientVersionRemote
) : WrapperRemote<ClientVersionRemote>

data class ClientVersionRemote(
    val manifestId: String,
    val branch: String,
    val version: String,
    val buildVersion: String,
    val engineVersion: String,
    val riotClientVersion: String,
    val riotClientBuild: String,
    val buildDate: String
)
