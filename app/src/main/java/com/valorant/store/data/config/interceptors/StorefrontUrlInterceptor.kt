package com.valorant.store.data.config.interceptors

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

object StorefrontUrlInterceptor : Interceptor {
    @Volatile
    private var shardProvider: (() -> String)? = null

    fun setShardProvider(shardProvider: () -> String) {
        synchronized(this) {
            this.shardProvider = shardProvider
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val shard = shardProvider?.let { it() }

        Log.d("DYNAMIC_URL_INTERCEPTOR", shard.toString())

        if (shard == null) throw UrlMissingException()

        val request = chain.request()

        val newUrl = request.url.newBuilder()
            .host(request.url.host.format(shard))
            .build()

        val newRequest = request.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}

class UrlMissingException : RuntimeException("Url override not set")
