package com.valorant.store.main.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.valorant.store.api.essential_data.EssentialDataEntity
import com.valorant.store.api.essential_data.EssentialDataState
import com.valorant.store.api.store.StoreRepository
import com.valorant.store.api.store.dto.StorefrontDTO
import com.valorant.store.global.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainScreenViewModel(essentialDataState: EssentialDataState) : ViewModel() {
    private val _storefront = MutableStateFlow<UiState<StorefrontDTO>>(UiState.Loading)
    val storefront: StateFlow<UiState<StorefrontDTO>> = _storefront

    private val storeRepository = StoreRepository.getInstance("eu") // TODO hardcoded

    init {
        viewModelScope.launch {
            essentialDataState.essentialData.collect { essentialData ->
                when (essentialData) {
                    is UiState.Loading -> {}
                    is UiState.Success -> loadStorefront(essentialData.data)
                    is UiState.Error -> {
                        _storefront.value = UiState.Error(essentialData.exception)
                    }
                }
            }
        }
    }

    private suspend fun loadStorefront(essentialEntity: EssentialDataEntity) {
        viewModelScope.launch {
            val response = storeRepository.getStorefront(
                puuid = essentialEntity.user.puuid,
                headersMap = essentialEntity.toHeadersMap()
            )

            Log.w("STORE_STATE", response.toString())
            _storefront.value = UiState.of { response.getOrElse { it } }
        }
    }
}

class MainScreenViewModelFactory(
    private val essentialDataState: EssentialDataState
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainScreenViewModel(essentialDataState) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
