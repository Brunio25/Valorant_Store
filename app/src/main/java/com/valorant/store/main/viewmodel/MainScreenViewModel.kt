package com.valorant.store.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valorant.store.api.config.ItemType
import com.valorant.store.api.riot.store.entity.StorefrontEntity
import com.valorant.store.api.state_control.riot_store.RiotStoreState
import com.valorant.store.api.val_api.skins.SkinsRepository
import com.valorant.store.api.val_api.skins.entity.SkinBatchEntity
import com.valorant.store.global.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainScreenViewModel(riotStoreState: RiotStoreState) : ViewModel() {
    private val _skinBatchLevels = MutableStateFlow<UiState<SkinBatchEntity>>(UiState.Loading)
    val skinBatchLevels: StateFlow<UiState<SkinBatchEntity>> = _skinBatchLevels

    private val skinsRepository = SkinsRepository

    init {
        viewModelScope.launch {
            riotStoreState.riotStore.collect { riotStore ->
                when (riotStore) {
                    is UiState.Loading -> {}
                    is UiState.Success -> loadSkinInfo(riotStore.data)
                    is UiState.Error -> {
                        _skinBatchLevels.value = UiState.Error(riotStore.exception)
                    }
                }
            }
        }
    }

    private suspend fun loadSkinInfo(storefront: StorefrontEntity) {
        skinsRepository.cachesLoaded.await().takeIf { it.isFailure }
            ?.exceptionOrNull()
            ?.let {
                _skinBatchLevels.value = UiState.Error(it)
                return
            }

        val bundleLevels = storefront.bundle.items
            .filter { it.item.itemType == ItemType.SKIN_LEVEL_CONTENT }
            .map { it.item.itemId }

        val levels = bundleLevels + storefront.singleItemOffers.items
            .filter { it.item.itemType == ItemType.SKIN_LEVEL_CONTENT }
            .map { it.item.itemId }

        val response = skinsRepository.getBatchSkins(levels)
        _skinBatchLevels.value = UiState.of(response)
    }
}
