package com.valorant.store.api.interceptors

import android.util.Log
import com.valorant.store.auth.AuthState
import com.valorant.store.global.UiState
import okhttp3.Interceptor
import okhttp3.Response
import kotlin.concurrent.Volatile

object AuthInterceptor : Interceptor {
    @Volatile
    private var tokenProvider: (() -> String?)? = null

    fun setTokenProvider(authState: AuthState) {
        synchronized(this) {
            this.tokenProvider = {
                when (val authToken = authState.authToken.value) {
                    is UiState.Success -> authToken.data
                    else -> null
                }
            }
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenProvider?.let { it() }

        Log.d("INTERCEPTOR", token.toString())

        if (token == null) {
            throw TokenMissingException()
        }

        val requestWithAuth = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${tokenProvider?.let { it() }}")
            .build()

        return chain.proceed(requestWithAuth)
    }
}

class TokenMissingException : RuntimeException("Token not set")
