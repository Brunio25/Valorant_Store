package com.valorant.store.app.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.valorant.store.app.feature.login.screens.LoginScreen
import com.valorant.store.app.feature.storefront.screens.StorefrontScreen
import com.valorant.store.app.ui.navigation.NavRoutes.Auth
import com.valorant.store.app.ui.navigation.NavRoutes.Home
import com.valorant.store.app.ui.navigation.NavRoutes.Splash
import com.valorant.store.splash.screens.SplashScreen

@Composable
fun ValorantStoreNavigation(navController: NavHostController) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = Splash.route
    ) {
        composable(route = Splash.route) { SplashScreen(navController) }
        composable(route = Auth.route) { LoginScreen(navController) }
        composable(route = Home.route) { StorefrontScreen() }
    }
}
