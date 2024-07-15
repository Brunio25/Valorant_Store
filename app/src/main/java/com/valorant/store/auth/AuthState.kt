package com.valorant.store.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valorant.store.api.interceptors.AuthInterceptor
import com.valorant.store.global.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

object AuthState : ViewModel() {
    private val _authToken = MutableStateFlow<UiState<String>>(UiState.Loading)
    val authToken: StateFlow<UiState<String>> = _authToken

    private val authTokenProvider = {
        when (val token = authToken.value) {
            is UiState.Success -> token.data
            else -> null
        }
    }

    fun setAuthToken(newToken: String?) {
        viewModelScope.launch {
            newToken?.let {
                _authToken.value = UiState.Success(newToken)
                AuthInterceptor.setTokenProvider(authTokenProvider)
            } ?: let { _authToken.value = UiState.Error(IllegalStateException("Null auth token")) }
        }
    }
}