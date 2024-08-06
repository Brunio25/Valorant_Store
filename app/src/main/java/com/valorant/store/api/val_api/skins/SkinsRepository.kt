package com.valorant.store.api.val_api.skins

import android.util.Log
import com.valorant.store.api.Repository
import com.valorant.store.api.val_api.skins.dto.content_tiers.ContentTiersDTO
import com.valorant.store.api.val_api.skins.dto.content_tiers.ContentTiersWrapperDTO
import com.valorant.store.api.val_api.skins.dto.currencies.CurrencyDTO
import com.valorant.store.api.val_api.skins.dto.currencies.CurrencyWrapperDTO
import com.valorant.store.api.val_api.skins.dto.skins.SkinDTO
import com.valorant.store.api.val_api.skins.dto.skins.SkinsBatchWrapperDTO
import com.valorant.store.api.val_api.skins.entity.ContentTierEntity
import com.valorant.store.api.val_api.skins.entity.ContentTierMapEntity
import com.valorant.store.api.val_api.skins.entity.CurrencyEntity
import com.valorant.store.api.val_api.skins.entity.CurrencyMapEntity
import com.valorant.store.api.val_api.skins.entity.SkinBatchEntity
import com.valorant.store.api.val_api.skins.entity.SkinEntity
import com.valorant.store.api.val_api.skins.entity.SkinMapEntity
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import java.util.UUID

object SkinsRepository : SkinsRepositoryInitializer() {
    fun getBatchSkins(levelIds: List<UUID>): Result<SkinBatchEntity> = levelIds
        .map { _cachedSkins[it] ?: return Result.failure(SkinNotFoundException(it)) }
        .let { SkinsMapper.toSkinBatchEntity(it) }


    fun getSkinByLevelId(levelId: UUID): Result<SkinEntity> =
        _cachedSkins[levelId]?.let { Result.success(it) } ?: Result.failure(
            SkinNotFoundException(levelId)
        )
}

open class SkinsRepositoryInitializer : Repository<SkinsApi>(SkinsApi::class.java, false) {
    override val baseUrl = "https://valorant-api.com/"

    private val repositoryScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    protected val _cachedSkins = CompletableDeferred<SkinMapEntity>()
    protected val _cachedCurrencies = CompletableDeferred<CurrencyMapEntity>()
    protected val _cachedContentTiers = CompletableDeferred<ContentTierMapEntity>()
    val cachesLoaded = CompletableDeferred<Result<Boolean>>()

    init {
        loadData()
    }

    private fun loadData() {
        repositoryScope.launch {
            val deferredSkins = async { loadSkins() }
            val deferredCurrencies = async { loadCurrencies() }
            val deferredContentTiers = async { loadContentTiers() }

            val results = awaitAll(deferredSkins, deferredCurrencies, deferredContentTiers)

            results.forEach { result ->
                result.getOrElse {
                    cachesLoaded.complete(Result.failure(it))
                    return@launch
                }
            }

            cachesLoaded.complete(Result.success(true))
        }
    }

    private suspend fun loadSkins(): Result<SkinMapEntity> {
        val response = getSkinsFromRemote(setCachedSkins = true)

        response.takeIf { it.isFailure }
            ?.exceptionOrNull()
            ?.also { cachesLoaded.complete(Result.failure(it)) }
            ?.let { Log.e("SKINS_INIT", it.toString()) }

        return response
    }

    private suspend fun getSkinsFromRemote(setCachedSkins: Boolean = false): Result<SkinMapEntity> =
        runCatching {
            val response = apiClient.skins()

            response.takeIf { it.isSuccessful }
                ?.body()?.let { SkinsMapper.toSkinMapEntity(it) }
                ?.also {
                    if (setCachedSkins) {
                        setCachedSkins(it)
                    }
                }?.let { Result.success(it) }
                ?: Result.failure(Exception(response.message()))
        }.getOrElse { Result.failure(it) }

    private suspend fun setCachedSkins(skinMapEntity: SkinMapEntity) {
        _cachedSkins.complete(skinMapEntity)
        Log.d("SKINS_CACHE_LOADED", _cachedSkins.await().isNotEmpty().toString())
    }

    private suspend fun loadCurrencies(): Result<CurrencyMapEntity> {
        val response = getCurrenciesFromRemote(setCachedCurrencies = true)

        response.takeIf { it.isFailure }
            ?.exceptionOrNull()
            ?.let { Log.e("CURRENCIES_INIT", it.toString()) }

        return response
    }

    private suspend fun getCurrenciesFromRemote(setCachedCurrencies: Boolean = false): Result<CurrencyMapEntity> =
        runCatching {
            val response = apiClient.currencies()

            response.takeIf { it.isSuccessful }
                ?.body()?.let { SkinsMapper.toCurrencyMapEntity(it) }
                ?.also {
                    if (setCachedCurrencies) {
                        setCachedCurrencies(it)
                    }
                }?.let { Result.success(it) }
                ?: Result.failure(Exception(response.message()))
        }.getOrElse { Result.failure(it) }

    private suspend fun setCachedCurrencies(currencyMapEntity: CurrencyMapEntity) {
        _cachedCurrencies.complete(currencyMapEntity)
        Log.d("CURRENCY_CACHE_LOADED", _cachedCurrencies.await().isNotEmpty().toString())
    }

    private suspend fun loadContentTiers(): Result<ContentTierMapEntity> {
        val response = getContentTiersFromRemote(setCachedContentTiers = true)

        response.takeIf { it.isFailure }
            ?.exceptionOrNull()
            ?.let { Log.e("CONTENT_TIERS_INIT", it.toString()) }

        return response
    }

    private suspend fun getContentTiersFromRemote(setCachedContentTiers: Boolean): Result<ContentTierMapEntity> =
        runCatching {
            val response = apiClient.contentTiers()

            response.takeIf { it.isSuccessful }
                ?.body()?.let { SkinsMapper.toContentTierMapEntity(it) }
                ?.also {
                    if (setCachedContentTiers) {
                        setCachedContentTiers(it)
                    }
                }?.let { Result.success(it) }
                ?: Result.failure(Exception(response.message()))
        }.getOrElse { Result.failure(it) }

    private suspend fun setCachedContentTiers(contentTierMapEntity: ContentTierMapEntity) {
        _cachedContentTiers.complete(contentTierMapEntity)
        Log.d("CONTENT_TIERS_CACHE_LOADED", _cachedContentTiers.await().isNotEmpty().toString())
    }
}

object SkinsMapper {
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

    fun toCurrencyMapEntity(currencyBatchWrapperDTO: CurrencyWrapperDTO): CurrencyMapEntity =
        currencyBatchWrapperDTO.data.associate {
            it.uuid to toCurrencyEntity(it)
        }

    private fun toCurrencyEntity(currencyDTO: CurrencyDTO): CurrencyEntity = with(currencyDTO) {
        CurrencyEntity(
            displayName = displayName,
            displayNameSingular = displayNameSingular,
            displayIcon = displayIcon,
            largeIcon = largeIcon
        )
    }

    fun toContentTierMapEntity(contentTiersWrapperDTO: ContentTiersWrapperDTO): ContentTierMapEntity =
        contentTiersWrapperDTO.data.associate {
            it.uuid to toContentTierEntity(it)
        }

    private fun toContentTierEntity(contentTiersDTO: ContentTiersDTO): ContentTierEntity =
        with(contentTiersDTO) {
            ContentTierEntity(
                displayName = displayName,
                devName = devName,
                rank = rank,
                highlightColor = highlightColor,
                displayIcon = displayIcon
            )
        }
}

@OptIn(ExperimentalCoroutinesApi::class)
private operator fun <T> CompletableDeferred<Map<UUID, T>>.get(levelId: UUID): T? =
    getCompleted()[levelId]

class SkinNotFoundException(id: UUID) : NoSuchElementException("Skin with skinLevel $id not found")
