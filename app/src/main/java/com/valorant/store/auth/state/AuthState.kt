package com.valorant.store.auth.state

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PageLoadingState : ViewModel() {
    private var _isPageLoading = mutableStateOf(true)
    val isPageLoading: State<Boolean> = _isPageLoading

    fun pageLoaded() {
        _isPageLoading.value = false
    }
}

class TokenState : ViewModel() {
    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = _token

    fun setToken(newToken: String?) {
        viewModelScope.launch {
            _token.value = newToken
        }
    }
}
