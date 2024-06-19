package com.valorant.store.navigation

sealed class NavRoutes(val route: String) {
    data object First : NavRoutes("first_screen")
    data object Auth : NavRoutes("auth_screen")
    data object Home : NavRoutes("home_screen")

    override fun toString(): String = route
}
