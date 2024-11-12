package com.valorant.store.global

import com.valorant.store.app.feature.login.AuthState

object ViewModelProvider {
    val authState by lazy { AuthState }
}
