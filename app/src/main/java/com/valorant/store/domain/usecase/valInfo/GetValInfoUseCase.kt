package com.valorant.store.domain.usecase.valInfo

import com.valorant.store.data.repository.contentTiers.ContentTiersRepository
import com.valorant.store.data.repository.currencies.CurrenciesRepository
import com.valorant.store.data.repository.skins.SkinsRepository
import com.valorant.store.domain.model.valInfo.ValInfoDataStream
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetValInfoUseCase @Inject constructor(
    private val contentTiersRepository: ContentTiersRepository,
    private val currenciesRepository: CurrenciesRepository,
    private val skinsRepository: SkinsRepository
) {
    operator fun invoke(): Flow<ValInfoDataStream?> = combine(
        contentTiersRepository.getContentTiersFlow,
        currenciesRepository.getCurrenciesFlow,
        skinsRepository.getSkinsFlow
    ) { contentTiers, currencies, skins ->
        if (skins == null) return@combine null

        ValInfoDataStream(
            contentTiers = contentTiers,
            currencies = currencies,
            skins = skins
        )
    }
}
