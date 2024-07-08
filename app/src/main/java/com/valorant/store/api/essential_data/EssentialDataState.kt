package com.valorant.store.api.essential_data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valorant.store.api.essential_data.client_version.ClientVersionEntity
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

object EssentialDataState : ViewModel() {
    private val _essentialData = MutableStateFlow<Result<EssentialDataEntity>?>(null)
    val essentialData: StateFlow<Result<EssentialDataEntity>?> = _essentialData

    private val _user = MutableStateFlow<Result<UserEntity>?>(null)
    private val _entitlement = MutableStateFlow<Result<EntitlementEntity>?>(null)
    private val _clientVersion = MutableStateFlow<Result<ClientVersionEntity>?>(null)

    fun loadEssentialData() {
        viewModelScope.launch {
            val loadUserDeferred = async { loadUserInfo() }
            val loadEntitlementDeferred = async { loadEntitlement() }
            val loadClientVersion = async { loadClientVersion() }

            awaitAll(loadUserDeferred, loadEntitlementDeferred, loadClientVersion)

            _essentialData.value = EssentialDataEntity.of(
                _user.value!!, _entitlement.value!!, _clientVersion.value!!
            )
        }
    }

    private suspend fun loadUserInfo() {
        _user.value = UserRepository.getUserInfo()
        Log.i("ESSENTIAL_USER", _user.value.toString())
    }

    private suspend fun loadEntitlement() {
        _entitlement.value = EntitlementRepository.getEntitlement()
        Log.i("ESSENTIAL_ENTITLEMENT", _entitlement.value.toString())
    }

    private suspend fun loadClientVersion() {
        _clientVersion.value = ClientVersionRepository.getClientVersion()
        Log.i("ESSENTIAL_CLIENT_VERSION", _clientVersion.value.toString())
    }
}

class EssentialDataEntity private constructor(
    public val user: UserEntity,
    public val entitlement: EntitlementEntity,
    public val clientVersion: ClientVersionEntity
) {
    companion object {
        fun of(
            user: Result<UserEntity>,
            entitlement: Result<EntitlementEntity>,
            clientVersion: Result<ClientVersionEntity>
        ): Result<EssentialDataEntity> {
            val userSuccess = user.getOrElse { return Result.failure(it) }
            val entitlementSuccess = entitlement.getOrElse { return Result.failure(it) }
            val clientVersionSuccess = clientVersion.getOrElse { return Result.failure(it) }

            return EssentialDataEntity(
                userSuccess,
                entitlementSuccess,
                clientVersionSuccess
            ).let { Result.success(it) }
        }
    }
}
