package com.valorant.store.main.screens

import android.util.Log
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.valorant.store.ErrorScreen
import com.valorant.store.api.state_control.RiotStoreState
import com.valorant.store.api.state_control.RiotStoreStateFactory
import com.valorant.store.api.riot.store.dto.StorefrontDTO
import com.valorant.store.auth.AuthState
import com.valorant.store.global.UiState
import com.valorant.store.main.viewmodel.MainScreenViewModel
import com.valorant.store.main.viewmodel.MainScreenViewModelFactory

@Composable
fun MainScreen(authState: AuthState) {
    val riotStoreState: RiotStoreState = viewModel(factory = RiotStoreStateFactory(authState))
    val viewModel: MainScreenViewModel =
        viewModel(factory = MainScreenViewModelFactory(riotStoreState))
    val riotStore by riotStoreState.riotStore.collectAsState()
    val storefrontState by viewModel.storefront.collectAsState()

    when (val storefront = riotStore) {
        is UiState.Loading -> CircularProgressIndicator()
        is UiState.Success -> MainScreenContent(storefront = storefront.data)
        is UiState.Error -> ErrorScreen()
    }
}

@Composable
fun MainScreenContent(storefront: StorefrontDTO) {
    val temp = try {
        storefront.featuredBundle.bundle.items.map { it.item.itemTypeID to it.basePrice }
    } catch (e: Exception) {
        e
    }

    if (temp !is Throwable) {
        Log.i("TOKEN_HOME", temp.toString())
        Text("Token: $temp")
        Log.i("TEST", storefront.skinsPanelLayout.singleItemOffers.toString())
    } else {
        Log.e("HOME", temp.message!!)
    }
}
