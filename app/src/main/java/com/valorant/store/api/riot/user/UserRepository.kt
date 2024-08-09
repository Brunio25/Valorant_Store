package com.valorant.store.api.riot.user

import com.valorant.store.api.Repository
import com.valorant.store.api.riot.user.dto.UserInfoDTO
import retrofit2.Response
import java.util.UUID

object UserRepository : Repository<UserApi>(UserApi::class.java) {
    override val baseUrl = "https://auth.riotgames.com"

    suspend fun getUserInfo(): Result<UserEntity> = runCatching {
        val response = apiClient.userInfo()
        response.getResult()
    }.getOrElse { Result.failure(it) }


    private fun Response<UserInfoDTO>.getResult() = takeIf { isSuccessful }
        ?.extractBody()
        ?: let { Result.failure(Exception(it.message())) }

    private fun Response<UserInfoDTO>.extractBody() = body()
        ?.let { UserMapper.toUserEntity(it) }
        ?: Result.failure(Exception("Null response body"))
}

private object UserMapper {
    fun toUserEntity(userInfoDTO: UserInfoDTO): Result<UserEntity> =
        UserEntity(userInfoDTO.sub, userInfoDTO.affinity["pp"], userInfoDTO.acct.gameName)
            .let { Result.success(it) }
}

data class UserEntity(val puuid: UUID, val shard: String?, val gamerName: String)
