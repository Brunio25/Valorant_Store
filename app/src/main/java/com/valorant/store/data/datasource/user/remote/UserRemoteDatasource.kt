package com.valorant.store.data.datasource.user.remote

import com.valorant.store.data.datasource.RemoteDatasource
import com.valorant.store.data.datasource.user.service.UserClient
import com.valorant.store.data.model.user.UserRemote
import javax.inject.Inject

class UserRemoteDatasource @Inject constructor(
    private val userClient: UserClient
) : RemoteDatasource {
    suspend fun getUser(): UserRemote? = userClient.userInfo().getOrNull()
}
