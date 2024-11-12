package com.valorant.store.app.feature.storefront.model

import com.valorant.store.app.model.storefront.StorefrontUI

sealed interface StorefrontScreenViewState {
    data object Loading : StorefrontScreenViewState

    data class Content(val storefront: StorefrontUI) : StorefrontScreenViewState
}
