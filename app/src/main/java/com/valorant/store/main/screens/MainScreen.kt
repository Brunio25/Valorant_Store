package com.valorant.store.main.screens

import android.util.Log
import androidx.compose.material3.CircularProgressIndicator
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
    val authToken by authState.authToken.collectAsState()
    val viewModel: EssentialDataState = viewModel()
    val essentialData by viewModel.essentialData.collectAsState()

    if (authToken == null) {
        return
    }

    LaunchedEffect(key1 = authToken) {
        viewModel.loadEssentialData()
    }

    if (essentialData == null) {
        CircularProgressIndicator()
        return
    }

    val temp = try {
        essentialData!!.getOrThrow().user.gamerName
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
