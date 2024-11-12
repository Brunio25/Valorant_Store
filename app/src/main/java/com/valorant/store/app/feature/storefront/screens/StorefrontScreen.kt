package com.valorant.store.app.feature.storefront.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.valorant.store.app.feature.storefront.model.StorefrontScreenViewState
import com.valorant.store.app.feature.storefront.viewmodel.StorefrontScreenViewModel

@Composable
fun StorefrontScreen() {
    val viewModel: StorefrontScreenViewModel = hiltViewModel()
    val state = viewModel.skinBatchLevels.collectAsStateWithLifecycle().value

    Box(modifier = Modifier.fillMaxSize()) {
        when (state) {
            is StorefrontScreenViewState.Content -> MainScreenContent(state = state)
            is StorefrontScreenViewState.Loading -> CircularProgressIndicator(
                modifier = Modifier.align(
                    Alignment.Center
                )
            )
        }
    }
}

@Composable
fun MainScreenContent(state: StorefrontScreenViewState.Content) {
    val imageUrls = with(state.storefront) {
        bundles.flatMap { bundle -> bundle.items.mapNotNull { it.item.skin.displayIcon } }
            .plus(
                skinsPanel.items.mapNotNull { it.item.skin.displayIcon }
            )
            .plus(
                nightMarket?.items?.flatMap { offer -> offer.items.mapNotNull { it.skin.displayIcon } }
                    ?: emptyList()
            )
    }

    Log.i("TOKEN_HOME", imageUrls.toString())
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(imageUrls) {
            AsyncImage(
                model = it,
                contentDescription = "Descriptor",
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .aspectRatio(1f)
            )
        }
    }
}
