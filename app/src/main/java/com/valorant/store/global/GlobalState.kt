package com.valorant.store.global

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valorant.store.api.entitlement.EntitlementEntity
import com.valorant.store.api.entitlement.EntitlementRepository
import com.valorant.store.api.user.UserEntity
import com.valorant.store.api.user.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

object GlobalState : ViewModel() {
    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = _token

    private val userRepository = UserRepository
    private val _user = MutableStateFlow<Result<UserEntity>?>(null)
    val user: StateFlow<Result<UserEntity>?> = _user

    fun setToken(newToken: String?) {
        viewModelScope.launch {
            _token.value = newToken
        }
    }

    fun getUserInfo() {
        viewModelScope.launch {
            val newUser = userRepository.getUserInfo()
            _user.value = newUser
            Log.i("REPOSITORY", _user.value.toString())
        }
    }
}
