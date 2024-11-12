package com.valorant.store.splash.screens

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.valorant.store.app.ui.navigation.NavRoutes

@Composable
fun SplashScreen(navController: NavController = rememberNavController()) {
    return Button(onClick = {
        navController.navigate(NavRoutes.Auth.route)
    }) {
        Text(text = "Login")
    }
}