package com.valorant.store.api.riot.user

import com.valorant.store.api.Repository
import com.valorant.store.api.riot.user.dto.UserInfoDTO
import java.util.UUID

object UserRepository : Repository<UserApi>(UserApi::class.java) {
    override val baseUrl = "https://auth.riotgames.com"

    suspend fun getUserInfo(): Result<UserEntity> = runCatching {
        val response = apiClient.userInfo()

        response.takeIf { it.isSuccessful }
            ?.body()
            ?.let { UserMapper.toUserEntity(it) }
            ?: throw Exception("Null response body")
    }
}

private object UserMapper {
    fun toUserEntity(userInfoDTO: UserInfoDTO): UserEntity =
        UserEntity(userInfoDTO.sub, userInfoDTO.affinity["pp"], userInfoDTO.acct.gameName)
}

data class UserEntity(val puuid: UUID, val shard: String?, val gamerName: String)
