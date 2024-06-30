package com.valorant.store.global

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valorant.store.api.entitlement.EntitlementEntity
import com.valorant.store.api.entitlement.EntitlementRepository
import com.valorant.store.api.user.UserEntity
import com.valorant.store.api.user.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

object GlobalState : ViewModel() {
    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = _token

    private val _isEssentialDataLoaded = MutableStateFlow(false)
    val isEssentialDataLoaded: StateFlow<Boolean> = _isEssentialDataLoaded

    private val userRepository = UserRepository
    private val _user = MutableStateFlow<Result<UserEntity>?>(null)
    val user: StateFlow<Result<UserEntity>?> = _user

    private val entitlementRepository = EntitlementRepository
    private val _entitlement = MutableStateFlow<Result<EntitlementEntity>?>(null)
    val entitlement: StateFlow<Result<EntitlementEntity>?> = _entitlement

    fun setToken(newToken: String?) {
        viewModelScope.launch {
            _token.value = newToken
        }
    }

    fun loadEssentialData() {
        viewModelScope.launch {
            val loadUserDeferred = async { loadUserInfo() }
            val loadEntitlementDeferred = async { loadEntitlement() }

            awaitAll(loadUserDeferred, loadEntitlementDeferred)

            _isEssentialDataLoaded.value =
                user.value != null && entitlement.value != null
        }
    }

    private suspend fun loadUserInfo() {
        _user.value = userRepository.getUserInfo()
        Log.i("GLOBAL_STATE_USER", _user.value.toString())
    }

    private suspend fun loadEntitlement() {
        _entitlement.value = entitlementRepository.getEntitlement()
        Log.i("GLOBAL_STATE_ENTITLEMENT", _entitlement.value.toString())
    }
}
