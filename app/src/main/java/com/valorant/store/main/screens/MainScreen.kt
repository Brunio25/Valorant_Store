package com.valorant.store.main.screens

import android.util.Log
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.valorant.store.ErrorScreen
import com.valorant.store.api.essential_data.EssentialDataState
import com.valorant.store.api.essential_data.EssentialDataStateFactory
import com.valorant.store.api.store.dto.StorefrontDTO
import com.valorant.store.auth.AuthState
import com.valorant.store.global.UiState
import com.valorant.store.main.viewmodel.MainScreenViewModel
import com.valorant.store.main.viewmodel.MainScreenViewModelFactory

@Composable
fun MainScreen(authState: AuthState) {
    val essentialDataState: EssentialDataState =
        viewModel(factory = EssentialDataStateFactory(authState))
    val viewModel: MainScreenViewModel =
        viewModel(factory = MainScreenViewModelFactory(essentialDataState))
    val storefrontState by viewModel.storefront.collectAsState()

    when (val storefront = storefrontState) {
        is UiState.Loading -> CircularProgressIndicator()
        is UiState.Success -> MainScreenContent(storefront = storefront.data)
        is UiState.Error -> ErrorScreen()
    }
}

@Composable
fun MainScreenContent(storefront: StorefrontDTO) {
    val temp = try {
        storefront.featuredBundle.bundle.items.map { it.item.itemID }
    } catch (e: Exception) {
        e
    }

    if (temp !is Throwable) {
        Log.i("TOKEN_HOME", temp.toString())
        Text("Token: $temp")
    } else {
        Log.e("HOME", temp.message!!)
    }
}
