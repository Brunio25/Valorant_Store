package com.valorant.store.domain.usecase.valInfo

import com.valorant.store.data.repository.clientVersion.ClientVersionRepository
import com.valorant.store.data.repository.contentTiers.ContentTiersRepository
import com.valorant.store.data.repository.currencies.CurrenciesRepository
import com.valorant.store.data.repository.skins.SkinsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoadCacheUseCase @Inject constructor(
    private val clientVersionRepository: ClientVersionRepository,
    private val contentTiersRepository: ContentTiersRepository,
    private val currenciesRepository: CurrenciesRepository,
    private val skinsRepository: SkinsRepository
) {
    suspend operator fun invoke() = withContext(Dispatchers.Default) {
        if (clientVersionRepository.isVersionUpdated() && isAllCacheInitialized()) return@withContext

        clientVersionRepository.reloadCacheFromRemote()
        contentTiersRepository.reloadCacheFromRemote()
        currenciesRepository.reloadCacheFromRemote()
        skinsRepository.reloadCacheFromRemote()
    }

    private suspend fun isAllCacheInitialized(): Boolean = arrayOf(
        clientVersionRepository.getClientVersion(),
        contentTiersRepository.getContentTiers(),
        currenciesRepository.getCurrencies(),
        skinsRepository.getSkins()
    ).all { it != null }
}
