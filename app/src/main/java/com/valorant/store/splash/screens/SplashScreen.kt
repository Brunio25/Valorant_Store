package com.valorant.store.splash.screens

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.valorant.store.global.ViewModelProvider
import com.valorant.store.navigation.NavRoutes

@Composable
fun SplashScreen(navController: NavController) {
    ViewModelProvider.splashState
    return Button(onClick = {
        navController.navigate(NavRoutes.Auth.route)
    }) {
        Text(text = "Login")
    }
}