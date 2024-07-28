package com.valorant.store.api.val_api.skin_levels

import android.net.Uri
import com.valorant.store.api.Repository
import com.valorant.store.api.val_api.skin_levels.dto.SkinLevelDTO
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import retrofit2.Response
import java.util.UUID

object SkinLevelRepository : Repository<SkinLevelApi>(SkinLevelApi::class.java, false) {
    override val baseUrl = "https://valorant-api.com/v1/"

    suspend fun getBatchSkinLevels(skinLevelIds: List<UUID>): Result<SkinLevelBatchEntity> =
        coroutineScope {
            runCatching {
                skinLevelIds.map { async { apiClient.skinLevel(it) } }
                    .awaitAll()
                    .batchResult()
            }.getOrElse { Result.failure(it) }
        }

    suspend fun getSkinLevel(skinLevelId: UUID): Result<SkinLevelEntity> = runCatching {
        val response = apiClient.skinLevel(skinLevelId)

        response.takeIf { it.isSuccessful }
            ?.body()?.let { SkinLevelMapper.toSkinLevelEntity(it) }
            ?: Result.failure(Exception(response.message()))
    }.getOrElse { Result.failure(Exception("Null response body")) }

    private fun List<Response<SkinLevelDTO>>.batchResult(): Result<SkinLevelBatchEntity> =
        when (allSuccessful()) {
            true -> mapNotNull { it.body() }.let { SkinLevelMapper.toSkinLevelBatchEntity(it) }
            false -> first { !it.isSuccessful }.let { Result.failure(Exception(it.message())) }
        }

    private fun <T> List<Response<T>>.allSuccessful() = all { it.isSuccessful }
}

object SkinLevelMapper {
    fun toSkinLevelBatchEntity(skinLevelDtos: List<SkinLevelDTO>): Result<SkinLevelBatchEntity> =
        skinLevelDtos.map {
            with(it.data) {
                SkinLevelEntity(
                    uuid = uuid,
                    displayName = displayName,
                    levelItem = levelItem,
                    displayIcon = displayIcon,
                    streamedVideo = streamedVideo,
                    assetPath = assetPath
                )
            }
        }.let { Result.success(SkinLevelBatchEntity(it)) }

    fun toSkinLevelEntity(skinLevelDto: SkinLevelDTO): Result<SkinLevelEntity> =
        with(skinLevelDto.data) {
            SkinLevelEntity(
                uuid = uuid,
                displayName = displayName,
                levelItem = levelItem,
                displayIcon = displayIcon,
                streamedVideo = streamedVideo,
                assetPath = assetPath
            )
        }.let { Result.success(it) }
}

data class SkinLevelBatchEntity(
    val skinLevels: List<SkinLevelEntity>
)

data class SkinLevelEntity(
    val uuid: UUID,
    val displayName: String?,
    val levelItem: String?,
    val displayIcon: Uri?,
    val streamedVideo: Uri?,
    val assetPath: String
)
