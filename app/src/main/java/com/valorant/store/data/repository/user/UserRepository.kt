package com.valorant.store.data.repository.user

import com.valorant.store.data.datasource.user.remote.UserRemoteDatasource
import com.valorant.store.data.mappers.user.toDomain
import com.valorant.store.domain.model.user.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userRemoteDatasource: UserRemoteDatasource
) {
    suspend fun getUser(): User? = userRemoteDatasource.getUser()?.toDomain()
}
