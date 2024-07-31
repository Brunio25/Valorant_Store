package com.valorant.store.api.state_control.riot_store

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valorant.store.api.client_platform.ClientPlatformRepository
import com.valorant.store.api.val_api.client_version.ClientVersionRepository
import com.valorant.store.api.riot.entitlement.EntitlementRepository
import com.valorant.store.api.riot.store.StoreRepository
import com.valorant.store.api.riot.store.dto.StorefrontDTO
import com.valorant.store.api.riot.user.UserRepository
import com.valorant.store.api.state_control.riot_store.entity.RiotStoreEssentialDataEntity
import com.valorant.store.auth.AuthState
import com.valorant.store.global.UiState
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RiotStoreState(authState: AuthState) : ViewModel() {
    private val _riotStore = MutableStateFlow<UiState<StorefrontDTO>>(UiState.Loading)
    val riotStore: StateFlow<UiState<StorefrontDTO>> = _riotStore

    @Volatile
    private lateinit var storeRepository: StoreRepository
    private val clientPlatform = ClientPlatformRepository.getClientPlatform()

    init {
        viewModelScope.launch {
            authState.authToken.collect {
                loadRiotStore()
            }
        }
    }

    private suspend fun loadRiotStore() {
        val essentialEntity = loadRiotStoreEssentialData().getOrElse {
            _riotStore.value = UiState.Error(it)
            return
        }.also { result ->
            result.user.shard
                ?.let { storeRepository = StoreRepository.getInstance(it) }
                ?: let { _riotStore.value = UiState.Error(NoSuchElementException("Shard is null")) }
        }

        val response = storeRepository.getStorefront(
            puuid = essentialEntity.user.puuid,
            headersMap = essentialEntity.toHeadersMap()
        )

        Log.d("STORE_STATE", response.toString())
        _riotStore.value = UiState.of(response)
    }

    private suspend fun loadRiotStoreEssentialData(): Result<RiotStoreEssentialDataEntity> =
        coroutineScope {
            val loadUserDeferred = async { loadUserInfo() }
            val loadEntitlementDeferred = async { loadEntitlement() }
            val loadClientVersion = async { loadClientVersion() }

            Log.d("RIOT_STORE_PLATFORM", clientPlatform.toString())
            RiotStoreEssentialDataEntity.of(
                loadUserDeferred.await(),
                loadEntitlementDeferred.await(),
                loadClientVersion.await(),
                clientPlatform
            )
        }

    private suspend fun loadUserInfo() = UserRepository.getUserInfo().also {
        Log.d("RIOT_STORE_USER", it.toString())
    }

    private suspend fun loadEntitlement() = EntitlementRepository.getEntitlement().also {
        Log.d("RIOT_STORE_ENTITLEMENT", it.toString())
    }

    private suspend fun loadClientVersion() = ClientVersionRepository.getClientVersion().also {
        Log.d("RIOT_STORE_CL_VERSION", it.toString())
    }
}
