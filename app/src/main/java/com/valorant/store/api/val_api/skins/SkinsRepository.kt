package com.valorant.store.api.val_api.skins

import android.util.Log
import com.valorant.store.api.Repository
import com.valorant.store.api.val_api.skins.dto.SkinDTO
import com.valorant.store.api.val_api.skins.dto.SkinLevelWrapperDTO
import com.valorant.store.api.val_api.skins.dto.SkinsBatchWrapperDTO
import com.valorant.store.api.val_api.skins.entity.SkinBatchEntity
import com.valorant.store.api.val_api.skins.entity.SkinEntity
import com.valorant.store.api.val_api.skins.entity.SkinLevelEntity
import com.valorant.store.api.val_api.skins.entity.SkinMapEntity
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.UUID

object SkinsRepository : Repository<SkinsApi>(SkinsApi::class.java, false) {
    override val baseUrl = "https://valorant-api.com/"

    private val repositoryScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _cachedSkins = CompletableDeferred<SkinMapEntity>()
    val skinsLoaded = CompletableDeferred<Result<Boolean>>()

    init {
        repositoryScope.launch {
            loadSkins()
        }
    }

    fun getBatchSkins(levelIds: List<UUID>): Result<SkinBatchEntity> = levelIds
        .map { _cachedSkins[it] ?: return Result.failure(SkinNotFoundException(it)) }
        .let { SkinMapper.toSkinBatchEntity(it) }


    fun getSkinByLevelId(levelId: UUID): Result<SkinEntity> =
        _cachedSkins[levelId]?.let { Result.success(it) } ?: Result.failure(
            SkinNotFoundException(levelId)
        )

    suspend fun getBatchSkinLevels(skinLevelIds: List<UUID>): Result<SkinLevelBatchEntity> =
        coroutineScope {
            runCatching {
                skinLevelIds.map { async { apiClient.skinLevel(it) } }.awaitAll().batchResult()
            }.getOrElse { Result.failure(it) }
        }

    suspend fun getSkinLevel(skinLevelId: UUID): Result<SkinLevelEntity> =
        Result.failure(Exception())

    private fun List<Response<SkinLevelWrapperDTO>>.batchResult(): Result<SkinLevelBatchEntity> =
        when (allSuccessful()) {
            true -> mapNotNull { it.body() }.let { SkinMapper.toSkinLevelBatchEntity(it) }
            false -> first { !it.isSuccessful }.let { Result.failure(Exception(it.message())) }
        }

    private fun <T> List<Response<T>>.allSuccessful() = all { it.isSuccessful }

    @OptIn(ExperimentalCoroutinesApi::class)
    private operator fun CompletableDeferred<SkinMapEntity>.get(levelId: UUID) =
        _cachedSkins.getCompleted()[levelId]

    private suspend fun loadSkins() {
        val response = getSkinsFromRemote(setCachedSkins = true)

        response.takeIf { it.isFailure }
            ?.exceptionOrNull()
            ?.also { skinsLoaded.complete(Result.failure(it)) }
            ?.let { Log.e("VAL_API_SKINS_INIT", it.toString()) }
    }

    private suspend fun getSkinsFromRemote(setCachedSkins: Boolean = false): Result<SkinMapEntity> =
        runCatching {
            val response = apiClient.skins()

            response.takeIf { it.isSuccessful }
                ?.body()?.let { SkinMapper.toSkinMapEntity(it) }
                ?.also {
                    if (setCachedSkins) {
                        setCachedSkins(it)
                    }
                }?.let { Result.success(it) }
                ?: Result.failure(Exception(response.message()))
        }.getOrElse { Result.failure(it) }

    private fun setCachedSkins(skinMapEntity: SkinMapEntity) {
        _cachedSkins.complete(skinMapEntity)
        skinsLoaded.complete(Result.success(true))
    }
}

object SkinMapper {
    fun toSkinMapEntity(skinsBatchWrapperDTO: SkinsBatchWrapperDTO): SkinMapEntity =
        skinsBatchWrapperDTO.data.associate {
            it.levels.first().uuid to toSkinEntity(it)
        }

    private fun toSkinEntity(skinDTO: SkinDTO): SkinEntity = with(skinDTO) {
        SkinEntity(
            uuid = uuid,
            displayName = displayName,
            themeUuid = themeUuid,
            contentTierUuid = contentTierUuid,
            displayIcon = displayIcon,
            wallpaper = wallpaper,
            assetPath = assetPath,
            chromas = chromas,
            levels = levels
        )
    }

    fun toSkinBatchEntity(skinEntities: List<SkinEntity>): Result<SkinBatchEntity> =
        SkinBatchEntity(skinEntities).let { Result.success(it) }

    fun toSkinLevelBatchEntity(skinLevelDtos: List<SkinLevelWrapperDTO>): Result<SkinLevelBatchEntity> =
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
}

class SkinNotFoundException(id: UUID) : NoSuchElementException("Skin with skinLevel $id not found")

data class SkinLevelBatchEntity(
    val skinLevels: List<SkinLevelEntity>
)
