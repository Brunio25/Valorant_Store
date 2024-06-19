package com.valorant.store.auth.view_models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {
    private var _isPageLoading = mutableStateOf(true)
    val isPageLoading: State<Boolean> = _isPageLoading

    fun pageFinished() {
        _isPageLoading.value = false
    }
}
