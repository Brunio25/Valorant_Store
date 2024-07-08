package com.valorant.store.global

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valorant.store.api.essential_data.client_version.ClientVersionRepository
import com.valorant.store.api.essential_data.entitlement.EntitlementEntity
import com.valorant.store.api.essential_data.entitlement.EntitlementRepository
import com.valorant.store.api.essential_data.user.UserEntity
import com.valorant.store.api.essential_data.user.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

object GlobalState : ViewModel() {
    private val _authToken = MutableStateFlow<String?>(null)
    val authToken: StateFlow<String?> = _authToken

    private val _isEssentialDataLoaded = MutableStateFlow(false)
    val isEssentialDataLoaded: StateFlow<Boolean> = _isEssentialDataLoaded

    private val _user = MutableStateFlow<Result<UserEntity>?>(null)
    val user: StateFlow<Result<UserEntity>?> = _user

    private val _entitlement = MutableStateFlow<Result<EntitlementEntity>?>(null)
    val entitlement: StateFlow<Result<EntitlementEntity>?> = _entitlement

    fun setAuthToken(newToken: String?) {
        viewModelScope.launch {
            _authToken.value = newToken
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
        _user.value = UserRepository.getUserInfo()
        Log.i("GLOBAL_STATE_USER", _user.value.toString())
    }

    private suspend fun loadEntitlement() {
        _entitlement.value = EntitlementRepository.getEntitlement()
        Log.i("GLOBAL_STATE_ENTITLEMENT", _entitlement.value.toString())
    }
}
