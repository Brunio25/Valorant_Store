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
import com.valorant.store.global.AppCache
import com.valorant.store.global.DatastoreKey
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.UUID

object ValInfoRepository : ValInfoRepositoryInitializer() {
    fun getBatchSkins(levelIds: List<UUID>): Result<SkinBatchEntity> = levelIds
        .map { levelId ->
            _skins[levelId]
                ?: return Result.failure<SkinBatchEntity>(SkinNotFoundException(levelId)).also {
                    Log.e("GET_SKIN_INFO", "Batch skins error", it.exceptionOrNull())
                }
        }
        .let { SkinsMapper.toSkinBatchEntity(it) }

    fun getSkinByLevelId(levelId: UUID): Result<SkinEntity> =
        _skins[levelId]?.let { Result.success(it) } ?: Result.failure(
            SkinNotFoundException(levelId)
        )
}

open class ValInfoRepositoryInitializer : Repository<ValInfoApi>(ValInfoApi::class.java, false) {
    override val baseUrl = "https://valorant-api.com/"

    private val repositoryScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    protected val _skins = CompletableDeferred<SkinMapEntity>()
    protected val _currencies = CompletableDeferred<CurrencyMapEntity>()
    protected val _contentTiers = CompletableDeferred<ContentTierMapEntity>()
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

    private suspend fun loadSkins(): Result<SkinMapEntity> = getData(
        response = { apiClient.skins() },
        transform = SkinsMapper::toSkinMapEntity,
        setData = ::setSkins,
        cacheKey = DatastoreKey.SKINS_CACHE
    ).onLoadError("SKINS_INIT")

    private suspend fun setSkins(skinMapEntity: SkinMapEntity) {
        _skins.complete(skinMapEntity)
        Log.d("SKINS_LOADED", _skins.await().isNotEmpty().toString())
    }

    private suspend fun loadCurrencies(): Result<CurrencyMapEntity> = getData(
        response = { apiClient.currencies() },
        transform = SkinsMapper::toCurrencyMapEntity,
        setData = ::setCurrencies,
        cacheKey = DatastoreKey.CURRENCIES_CACHE
    ).onLoadError("CURRENCIES_INIT")

    private suspend fun setCurrencies(currencyMapEntity: CurrencyMapEntity) {
        _currencies.complete(currencyMapEntity)
        Log.d("CURRENCY_LOADED", _currencies.await().isNotEmpty().toString())
    }

    private suspend fun loadContentTiers(): Result<ContentTierMapEntity> = getData(
        response = { apiClient.contentTiers() },
        transform = SkinsMapper::toContentTierMapEntity,
        setData = ::setContentTiers,
        cacheKey = DatastoreKey.CONTENT_TIERS_CACHE
    ).onLoadError("CONTENT_TIERS_INIT")


    private suspend fun setContentTiers(contentTierMapEntity: ContentTierMapEntity) {
        _contentTiers.complete(contentTierMapEntity)
        Log.d("CONTENT_TIERS_LOADED", _contentTiers.await().isNotEmpty().toString())
    }

    private suspend inline fun <R, reified T> getData(
        response: (() -> Response<R>),
        noinline transform: (R) -> T,
        noinline setData: (suspend (T) -> Unit),
        cacheKey: DatastoreKey,
    ): Result<T> = runCatching {
        AppCache.readCache<T>(cacheKey).getOrNull()
            ?.also { setData(it) }
            ?: getFromRemote(
                response = response(),
                transform = transform,
                setData = setData,
                setCache = AppCache.writeCache(cacheKey)
            ).getOrThrow()
    }

    private suspend fun <R, T> getFromRemote(
        response: Response<R>,
        transform: (R) -> T,
        setData: (suspend (T) -> Unit),
        setCache: (suspend (T) -> Unit)
    ): Result<T> =
        runCatching {
            response.takeIf { it.isSuccessful }
                ?.body()?.let { transform(it) }
                ?.also { setData(it) }
                ?.also { setCache(it) }
                ?: throw Exception(response.message())
        }

    private fun <T> Result<T>.onLoadError(logErrorTag: String): Result<T> {
        takeIf { it.isFailure }
            ?.exceptionOrNull()
            ?.also { cachesLoaded.complete(Result.failure(it)) }
            ?.let { Log.e(logErrorTag, it.toString()) }

        return this
    }
}

private object SkinsMapper {
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
