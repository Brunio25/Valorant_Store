package com.valorant.store.data.config.interceptors

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import kotlin.concurrent.Volatile

object AuthInterceptor : Interceptor {
    @Volatile
    private var tokenProvider: (() -> String?)? = null

    fun setTokenProvider(authTokenProvider: () -> String?) {
        synchronized(this) {
            tokenProvider = authTokenProvider
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenProvider?.let { it() }

        Log.d("AUTH_INTERCEPTOR", token.toString())

        if (token == null) throw TokenMissingException()

        val requestWithAuth = chain.request().newBuilder()
            .oAuth(token)
            .build()

        return chain.proceed(requestWithAuth)
    }

    private fun Request.Builder.oAuth(token: String) =
        addHeader("Authorization", "Bearer $token")
}

class TokenMissingException : RuntimeException("Token not set")
