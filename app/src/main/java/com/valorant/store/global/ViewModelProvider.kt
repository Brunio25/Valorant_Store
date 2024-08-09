package com.valorant.store.global

import com.valorant.store.api.state_control.riot_store.RiotStoreState
import com.valorant.store.auth.AuthState
import com.valorant.store.main.viewmodel.MainScreenViewModel

object ViewModelProvider {
    val authState by lazy { AuthState }
    val riotStoreState by lazy { RiotStoreState(authState) }
    val mainScreenViewModel by lazy { MainScreenViewModel(riotStoreState) }
}
