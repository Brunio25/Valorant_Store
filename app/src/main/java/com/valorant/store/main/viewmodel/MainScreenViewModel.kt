package com.valorant.store.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.valorant.store.api.riot.store.StoreRepository
import com.valorant.store.api.riot.store.dto.StorefrontDTO
import com.valorant.store.api.state_control.RiotStoreState
import com.valorant.store.global.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.concurrent.Volatile

class MainScreenViewModel(riotStoreState: RiotStoreState) : ViewModel() {
    private val _storefront = MutableStateFlow<UiState<StorefrontDTO>>(UiState.Loading)
    val storefront: StateFlow<UiState<StorefrontDTO>> = _storefront

    @Volatile
    private lateinit var storeRepository: StoreRepository

//    init {
//        viewModelScope.launch {
//            riotStoreEssentialDataState.riotStoreEssentialData.collect { essentialData ->
//                when (essentialData) {
//                    is UiState.Loading -> {}
//                    is UiState.Success -> loadStorefront(essentialData.data)
//                    is UiState.Error -> {
//                        _storefront.value = UiState.Error(essentialData.exception)
//                    }
//                }
//            }
//        }
//    }
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
