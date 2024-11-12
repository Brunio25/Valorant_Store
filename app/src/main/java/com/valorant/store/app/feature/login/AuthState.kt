package com.valorant.store.app.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valorant.store.data.config.interceptors.AuthInterceptor
import com.valorant.store.global.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

object AuthState : ViewModel() {
    private val _authToken = MutableStateFlow<State<String>>(State.Loading)
    val authToken: StateFlow<State<String>> = _authToken

    private val authTokenProvider = {
        when (val token = authToken.value) {
            is State.Success -> token.data
            else -> null
        }
    }

    fun setAuthToken(newToken: String?) {
        viewModelScope.launch {
            newToken?.let {
                _authToken.value = State.Success(newToken)
                AuthInterceptor.setTokenProvider(authTokenProvider)
            } ?: let { _authToken.value = State.Error(IllegalStateException("Null auth token")) }
        }
    }
}