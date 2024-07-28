package com.valorant.store.main.screens

import android.util.Log
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.valorant.store.ErrorScreen
import com.valorant.store.api.state_control.riot_store.RiotStoreState
import com.valorant.store.api.state_control.riot_store.RiotStoreStateFactory
import com.valorant.store.api.val_api.skin_levels.SkinLevelBatchEntity
import com.valorant.store.auth.AuthState
import com.valorant.store.global.UiState
import com.valorant.store.main.viewmodel.MainScreenViewModel
import com.valorant.store.main.viewmodel.MainScreenViewModelFactory

@Composable
fun MainScreen(authState: AuthState) {
    val riotStoreState: RiotStoreState = viewModel(factory = RiotStoreStateFactory(authState))
    val viewModel: MainScreenViewModel =
        viewModel(factory = MainScreenViewModelFactory(riotStoreState))
    val skinBatchLevel by viewModel.skinBatchLevels.collectAsState()

    when (val storefront = skinBatchLevel) {
        is UiState.Loading -> CircularProgressIndicator()
        is UiState.Success -> MainScreenContent(skinLevelBatch = storefront.data)
        is UiState.Error -> ErrorScreen()
    }
}

@Composable
fun MainScreenContent(skinLevelBatch: SkinLevelBatchEntity) {
    val temp = try {
        skinLevelBatch.skinLevels[1].displayIcon
    } catch (e: Exception) {
        e
    }

    if (temp !is Throwable) {
        Log.i("TOKEN_HOME", temp.toString())
        AsyncImage(model = temp, contentDescription = "descriptor")
    } else {
        Log.e("HOME", temp.message!!)
    }
}
