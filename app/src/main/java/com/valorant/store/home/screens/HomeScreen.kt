package com.valorant.store.home.screens

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.valorant.store.global.GlobalState

@Composable
fun HomeScreen(globalState: GlobalState) {
    val token by globalState.token.collectAsState()
    val user by globalState.user.collectAsState()

    if (token == null) {
        return
    }

    LaunchedEffect(key1 = token) {
        globalState.getUserInfo()
    }

    if (user?.getOrNull() == null) {
        return
    }

    val temp = user?.getOrNull()?.gamerName ?: "null"

    Log.w("TOKEN_HOME", temp)
    Text("Token: $temp")
}
