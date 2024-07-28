package com.valorant.store.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.valorant.store.api.riot.store.dto.StorefrontDTO
import com.valorant.store.api.state_control.riot_store.RiotStoreState
import com.valorant.store.api.util.ItemType
import com.valorant.store.api.val_api.skin_levels.SkinLevelBatchEntity
import com.valorant.store.api.val_api.skin_levels.SkinLevelRepository
import com.valorant.store.global.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainScreenViewModel(riotStoreState: RiotStoreState) : ViewModel() {
    private val _skinBatchLevels = MutableStateFlow<UiState<SkinLevelBatchEntity>>(UiState.Loading)
    val skinBatchLevels: StateFlow<UiState<SkinLevelBatchEntity>> = _skinBatchLevels

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

    private suspend fun loadSkinInfo(storefront: StorefrontDTO) {
        val levels = storefront.featuredBundle.bundle.items.map { it.item }
            .filter { it.itemTypeID == ItemType.SKIN_LEVEL_CONTENT }
            .map { it.itemID }

        val response = SkinLevelRepository.getBatchSkinLevels(levels)
        _skinBatchLevels.value = UiState.of(response)
    }
}

class MainScreenViewModelFactory(
    private val riotStoreState: RiotStoreState
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainScreenViewModel(riotStoreState) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
