package com.valorant.store.app.feature.login.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valorant.store.domain.usecase.valInfo.LoadCacheUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val loadCacheUseCase: LoadCacheUseCase
) : ViewModel() {
    private var _isPageLoading = mutableStateOf(true)
    val isPageLoading: State<Boolean> = _isPageLoading

    init {
        viewModelScope.launch {
            loadCacheUseCase()
        }
    }

    fun pageLoaded() {
        _isPageLoading.value = false
    }
}
