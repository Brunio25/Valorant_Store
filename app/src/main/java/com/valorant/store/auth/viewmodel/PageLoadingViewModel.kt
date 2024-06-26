package com.valorant.store.auth.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

object PageLoadingViewModel : ViewModel() {
    private var _isPageLoading = mutableStateOf(true)
    val isPageLoading: State<Boolean> = _isPageLoading

    fun pageLoaded() {
        _isPageLoading.value = false
    }
}
