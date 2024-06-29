package com.valorant.store.api.user

import com.valorant.store.api.Repository
import com.valorant.store.api.user.dto.UserInfoDTO
import retrofit2.Response
import java.util.UUID

private const val BASE_URL = "https://auth.riotgames.com"

object UserRepository : Repository<UserApi>(UserApi::class.java, BASE_URL) {
    private val userMapper = UserMapper

    suspend fun getUserInfo(): Result<UserEntity> = try {
        val response = apiClient.userInfo()
        response.getResult()
    } catch (e: Exception) {
        Result.failure(e)
    }

    private fun Response<UserInfoDTO>.getResult() =
        takeIf { isSuccessful }?.extractBody() ?: let { Result.failure(Exception(it.message())) }

    private fun Response<UserInfoDTO>.extractBody() = body()
        ?.let { userMapper.toUserEntity(it) }
        ?: Result.failure(Exception("Null response body"))
}

object UserMapper {
    fun toUserEntity(userInfoDTO: UserInfoDTO): Result<UserEntity> =
        UserEntity.of(userInfoDTO.sub, userInfoDTO.affinity["pp"], userInfoDTO.acct.gameName)
            .let { Result.success(it) }
}

data class UserEntity(val puuid: UUID, val shard: String?, val gamerName: String) {
    companion object {
        fun of(puuid: UUID, shard: String?, gamerName: String) = UserEntity(puuid, shard, gamerName)
    }
}
