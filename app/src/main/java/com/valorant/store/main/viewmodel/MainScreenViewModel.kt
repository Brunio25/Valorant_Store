package com.valorant.store.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valorant.store.api.config.ItemType
import com.valorant.store.api.riot.store.entity.StorefrontEntity
import com.valorant.store.api.state_control.riot_store.RiotStoreState
import com.valorant.store.api.val_api.content.ValInfoRepository
import com.valorant.store.api.val_api.content.entity.SkinBatchEntity
import com.valorant.store.global.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainScreenViewModel(riotStoreState: RiotStoreState) : ViewModel() {
    private val _skinBatchLevels = MutableStateFlow<State<SkinBatchEntity>>(State.Loading)
    val skinBatchLevels: StateFlow<State<SkinBatchEntity>> = _skinBatchLevels

    private val contentRepository = ValInfoRepository
//    private val contentMerger = RiotStoreValApiContentMerger(contentRepository)

    init {
        viewModelScope.launch {
            riotStoreState.riotStore.collect { riotStore ->
                when (riotStore) {
                    is State.Loading -> {}
                    is State.Success -> loadSkinInfo(riotStore.data)
                    is State.Error -> {
                        _skinBatchLevels.value = State.Error(riotStore.exception)
                    }
                }
            }
        }
    }

    private suspend fun loadSkinInfo(storefront: StorefrontEntity) {
        contentRepository.cachesLoaded.await().takeIf { it.isFailure }
            ?.exceptionOrNull()
            ?.let {
                _skinBatchLevels.value = State.Error(it)
                return
            }

        val bundleLevels = storefront.bundle.items
            .filter { it.item.itemType == ItemType.SKIN_LEVEL_CONTENT }
            .map { it.item.itemId }

        val singleItemLevels = storefront.skinsPanel.items.map { it.item.itemId }

        val nightMarketLevels = storefront.nightMarket?.items
            ?.filter { it.items.first().itemType == ItemType.SKIN_LEVEL_CONTENT }
            ?.map { it.items.first().itemId }
            ?: emptyList()

        val levels = bundleLevels + singleItemLevels + nightMarketLevels

        val response = contentRepository.getBatchSkins(levels)
        _skinBatchLevels.value = State.Success(response)
    }
}
