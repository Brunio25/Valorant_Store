package com.valorant.store.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

object AuthState : ViewModel() {
    private val _authToken = MutableStateFlow<String?>(null)
    val authToken: StateFlow<String?> = _authToken

    fun setAuthToken(newToken: String?) {
        viewModelScope.launch {
            _authToken.value = newToken
        }
    }
}