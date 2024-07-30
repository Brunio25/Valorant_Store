package com.valorant.store.main.screens

import android.util.Log
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.valorant.store.ErrorScreen
import com.valorant.store.api.state_control.riot_store.RiotStoreState
import com.valorant.store.api.state_control.riot_store.RiotStoreStateFactory
import com.valorant.store.api.val_api.skins.entity.SkinBatchEntity
import com.valorant.store.auth.AuthState
import com.valorant.store.global.UiState
import com.valorant.store.main.viewmodel.MainScreenViewModel
import com.valorant.store.main.viewmodel.MainScreenViewModelFactory

@Composable
fun MainScreen(authState: AuthState) {
    val riotStoreState: RiotStoreState = viewModel(factory = RiotStoreStateFactory(authState))
    val viewModel: MainScreenViewModel =
        viewModel(factory = MainScreenViewModelFactory(riotStoreState))
    val skinBatch by viewModel.skinBatchLevels.collectAsState()

    when (val storefront = skinBatch) {
        is UiState.Loading -> CircularProgressIndicator()
        is UiState.Success -> MainScreenContent(skinBatch = storefront.data)
        is UiState.Error -> ErrorScreen()
    }
}

@Composable
fun MainScreenContent(skinBatch: SkinBatchEntity) {
    val imageUrls = skinBatch.skins.flatMap { skin -> skin.levels.map { it.displayIcon } }

    Log.i("TOKEN_HOME", imageUrls.toString())
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
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
