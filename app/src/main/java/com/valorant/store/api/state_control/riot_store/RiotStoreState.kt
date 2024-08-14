package com.valorant.store.api.state_control.riot_store

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valorant.store.api.client_platform.ClientPlatformRepository
import com.valorant.store.api.riot.entitlement.EntitlementRepository
import com.valorant.store.api.riot.store.StoreRepository
import com.valorant.store.api.riot.store.entity.StorefrontEntity
import com.valorant.store.api.riot.user.UserRepository
import com.valorant.store.api.state_control.riot_store.entity.RiotStoreEssentialDataEntity
import com.valorant.store.api.val_api.client_version.ClientVersionEntity
import com.valorant.store.auth.AuthState
import com.valorant.store.global.State
import com.valorant.store.splash.SplashState
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class RiotStoreState(authState: AuthState, splashState: SplashState) : ViewModel() {
    private val _riotStore = MutableStateFlow<State<StorefrontEntity>>(State.Loading)
    val riotStore: StateFlow<State<StorefrontEntity>> = _riotStore

    @Volatile
    private lateinit var storeRepository: StoreRepository
    private val clientPlatform = ClientPlatformRepository.getClientPlatform()

    init {
        viewModelScope.launch {
            combine(authState.authToken, splashState.clientVersion) { _, clientVersion ->
                clientVersion
            }.collect {
                when (it) {
                    is State.Loading -> {}
                    is State.Success -> loadRiotStore(it.data)
                    is State.Error -> {
                        _riotStore.value = State.Error(it.exception)
                    }
                }
            }
        }
    }

    private suspend fun loadRiotStore(clientVersion: ClientVersionEntity) {
        val essentialEntity = loadRiotStoreEssentialData(clientVersion).getOrElse {
            _riotStore.value = State.Error(it)
            return
        }.also { result ->
            result.user.shard
                ?.let { storeRepository = StoreRepository.getInstance(it) }
                ?: let { _riotStore.value = State.Error(NoSuchElementException("Shard is null")) }
        }

        val response = storeRepository.getStorefront(
            puuid = essentialEntity.user.puuid,
            headersMap = essentialEntity.toHeadersMap()
        )

        Log.d("STORE_STATE", response.toString())
        _riotStore.value = State.of(response)
    }

    private suspend fun loadRiotStoreEssentialData(
        clientVersion: ClientVersionEntity
    ): Result<RiotStoreEssentialDataEntity> = coroutineScope {
        val loadUserDeferred = async { loadUserInfo() }
        val loadEntitlementDeferred = async { loadEntitlement() }

        Log.d("RIOT_STORE_PLATFORM", clientPlatform.toString())
        RiotStoreEssentialDataEntity.of(
            loadUserDeferred.await(),
            loadEntitlementDeferred.await(),
            clientVersion,
            clientPlatform
        )
    }

    private suspend fun loadUserInfo() = UserRepository.getUserInfo().also {
        Log.d("RIOT_STORE_USER", it.toString())
    }

    private suspend fun loadEntitlement() = EntitlementRepository.getEntitlement().also {
        Log.d("RIOT_STORE_ENTITLEMENT", it.toString())
    }
}
