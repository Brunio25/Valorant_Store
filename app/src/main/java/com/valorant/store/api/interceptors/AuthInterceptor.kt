package com.valorant.store.api.interceptors

import com.valorant.store.auth.AuthState
import okhttp3.Interceptor
import okhttp3.Response
import kotlin.concurrent.Volatile

object AuthInterceptor : Interceptor {
    @Volatile
    private var tokenProvider: (() -> String?)? = null

    fun setTokenProvider(authState: AuthState) {
        synchronized(this) {
            this.tokenProvider = { authState.authToken.value }
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenProvider?.let { it() }

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
