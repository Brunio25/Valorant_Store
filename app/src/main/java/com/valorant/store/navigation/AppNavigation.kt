package com.valorant.store.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.valorant.store.FirstScreen
import com.valorant.store.auth.screens.AuthScreen
import com.valorant.store.main.screens.MainScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoutes.First.route) {
        composable(NavRoutes.First.route) {
            FirstScreen(navController)
        }
        composable(NavRoutes.Auth.route) {
            AuthScreen(navController)
        }
        composable(NavRoutes.Home.route) {
            MainScreen()
        }
    }
}
