package com.valorant.store.app.feature.storefront.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valorant.store.app.feature.storefront.model.StorefrontScreenViewState
import com.valorant.store.domain.usecase.storefront.GetStorefrontUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorefrontScreenViewModel @Inject constructor(
    private val getStorefrontUseCase: GetStorefrontUseCase
) : ViewModel() {
    private val _skinBatchLevels =
        MutableStateFlow<StorefrontScreenViewState>(StorefrontScreenViewState.Loading)
    val skinBatchLevels: StateFlow<StorefrontScreenViewState> = _skinBatchLevels

    init {
        observeStorefront()
    }

    private fun observeStorefront() {
        viewModelScope.launch {
            getStorefrontUseCase()
                .onEach { storefront ->
                    if (storefront == null) {
                        throw Exception() // TODO: Change this
                    }

                    _skinBatchLevels.update {
                        StorefrontScreenViewState.Content(storefront)
                    }

                }.launchIn(this)
        }
    }
}
