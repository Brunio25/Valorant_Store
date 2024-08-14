package com.valorant.store.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.valorant.store.auth.screens.AuthScreen
import com.valorant.store.main.screens.MainScreen
import com.valorant.store.splash.screens.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoutes.Splash.route) {
        composable(NavRoutes.Splash.route) {
            SplashScreen(navController)
        }
        composable(NavRoutes.Auth.route) {
            AuthScreen(navController)
        }
        composable(NavRoutes.Home.route) {
            MainScreen()
        }
    }
}
