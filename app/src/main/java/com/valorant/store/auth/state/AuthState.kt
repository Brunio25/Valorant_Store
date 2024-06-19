package com.valorant.store.auth.state

sealed class AuthState {
    data object UnAuthenticated : AuthState()
    data object Authenticated : AuthState()
    data object Error : AuthState()
}
