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

        val cachedClientVersion: Result<ClientVersionEntity> = AppCache.readCache(
            application.applicationContext,
            DatastoreKey.VAL_API_VERSION
        )

        val currentClientVersionRes = clientVersionDeferred.await()

        if (cachedClientVersion.isFailure) {
            deleteAllCache()
            _clientVersion.value = State.of(currentClientVersionRes)
            return@coroutineScope
        }


        val clientVersion = runCatching {
            val currentClientVersion = currentClientVersionRes.getOrThrow()

            cachedClientVersion.getOrThrow()
                .takeIf { it.valApiVersion != currentClientVersion.valApiVersion }
                ?.also { deleteAllCache() }

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
