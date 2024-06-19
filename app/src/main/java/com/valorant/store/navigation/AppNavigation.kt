package com.valorant.store.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.valorant.store.FirstScreen
import com.valorant.store.auth.screens.AuthScreen
import com.valorant.store.auth.state.TokenState
import com.valorant.store.home.screens.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val tokenState: TokenState = viewModel()

    NavHost(navController = navController, startDestination = NavRoutes.First.route) {
        composable(NavRoutes.First.route) {
            FirstScreen(navController)
        }
        composable(NavRoutes.Auth.route) {
            AuthScreen(navController, tokenState)
        }
        composable(NavRoutes.Home.route) {
            HomeScreen(tokenState)
        }
    }
}
