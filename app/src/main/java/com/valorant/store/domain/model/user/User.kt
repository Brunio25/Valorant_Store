package com.valorant.store.domain.model.user

import java.util.UUID

data class User(val id: UUID, val shard: String?, val gamerName: String)
