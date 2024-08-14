package com.valorant.store.global

import android.app.Application
import com.valorant.store.api.state_control.riot_store.RiotStoreState
import com.valorant.store.auth.AuthState
import com.valorant.store.main.viewmodel.MainScreenViewModel
import com.valorant.store.splash.SplashState

object ViewModelProvider {
    private lateinit var application: Application

    fun initialize(application: Application) {
        this.application = application
    }

    val splashState by lazy { SplashState(application) }
    val authState by lazy { AuthState }
    val riotStoreState by lazy { RiotStoreState(authState, splashState) }
    val mainScreenViewModel by lazy { MainScreenViewModel(riotStoreState) }
}
