package com.valorant.store.data.mappers.user

import com.valorant.store.data.model.user.UserRemote
import com.valorant.store.domain.model.user.User

fun UserRemote.toDomain(): User = User(sub, affinity["pp"], acct.gameName)
