package com.valorant.store.home.screens

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.valorant.store.auth.state.TokenState

@Composable
fun HomeScreen(tokenState: TokenState) {

    val token = tokenState.token.collectAsState().value
    Log.w("TOKEN_HOME", token ?: "null")
    Text("Token: $token")
}
