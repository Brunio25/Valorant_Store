package com.valorant.store.splash

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.valorant.store.api.val_api.client_version.ClientVersionEntity
import com.valorant.store.api.val_api.client_version.ClientVersionRepository
import com.valorant.store.global.AppCache
import com.valorant.store.global.DatastoreKey
import com.valorant.store.global.State
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashState(private val application: Application) : AndroidViewModel(application) {
    private val _clientVersion = MutableStateFlow<State<ClientVersionEntity>>(State.Loading)
    val clientVersion: StateFlow<State<ClientVersionEntity>> = _clientVersion

    init {
        viewModelScope.launch {
            AppCache.initialize(application)
            loadClientVersion()
        }
    }

    private suspend fun loadClientVersion() = coroutineScope {
        val clientVersionDeferred = async { ClientVersionRepository.getClientVersion() }
        val cachedClientVersionDeferred: Deferred<Result<ClientVersionEntity>> = async {
            AppCache.readCache(DatastoreKey.VAL_API_VERSION)
        }

        val currentClientVersionRes = clientVersionDeferred.await()
        val cachedClientVersionRes = cachedClientVersionDeferred.await()

        val clientVersion = runCatching {
            val currentClientVersion = currentClientVersionRes.getOrThrow()

            cachedClientVersionRes.getOrNull()
                ?.takeIf { it.valApiVersion == currentClientVersion.valApiVersion }
                ?: let {
                    deleteAllCache()
                    AppCache.writeCache(DatastoreKey.VAL_API_VERSION, currentClientVersion)
                }

            currentClientVersion
        }

        loadClientVersion(clientVersion)
    }

    private suspend fun deleteAllCache() {
        DatastoreKey.entries.forEach { AppCache.deleteCache(it) }
    }

    private fun loadClientVersion(clientVersion: Result<ClientVersionEntity>) {
        _clientVersion.value = State.of(clientVersion)
        Log.d("CLIENT_VERSION_CACHE", _clientVersion.value.toString())
    }
}
