package com.valorant.store.main.screens

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.valorant.store.api.essential_data.EssentialDataState
import com.valorant.store.auth.AuthState

@Composable
fun MainScreen(authState: AuthState) {
    val viewModel: EssentialDataState = viewModel()
    val authToken by authState.authToken.collectAsState()
    val isEssentialDataLoaded by viewModel.isEssentialDataLoaded.collectAsState()
    val user by viewModel.user.collectAsState()

    if (authToken == null) {
        return
    }

    LaunchedEffect(key1 = authToken) {
        viewModel.loadEssentialData()
    }

    if (!isEssentialDataLoaded) {
        return
    }

    val temp = user?.getOrNull()?.gamerName ?: "null"

    Log.w("TOKEN_HOME", temp)
    Text("Token: $temp")
}
