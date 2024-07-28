package com.valorant.store.api

import android.net.Uri
import com.google.gson.GsonBuilder
import com.valorant.store.api.interceptors.AuthInterceptor
import com.valorant.store.api.util.ItemType
import com.valorant.store.api.util.ItemTypeCustomDeserializer
import com.valorant.store.api.util.LocalDateTimeCustomDeserializer
import com.valorant.store.api.util.UriCustomDeserializer
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap

object ClientProvider {
    private val apiMap: MutableMap<Class<out Api>, Api> = ConcurrentHashMap()

    @Suppress("UNCHECKED_CAST")
    fun <T> getInstance(
        apiClass: Class<T>,
        baseUrl: String,
        includeAuth: Boolean
    ): T where T : Api {
        return apiMap.getOrPut(apiClass) {
            createRetrofitClient(baseUrl, includeAuth).create(apiClass)
        } as T
    }

    private fun createRetrofitClient(baseUrl: String, includeAuthInterceptor: Boolean): Retrofit =
        Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(createGson())).client(
                OkHttpClient.Builder()
                    .also {
                        if (includeAuthInterceptor) {
                            it.addInterceptor(AuthInterceptor)
                        }
                    }.build()
            ).build()

    private fun createGson() = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeCustomDeserializer())
        .registerTypeAdapter(Uri::class.java, UriCustomDeserializer())
        .registerTypeAdapter(ItemType::class.java, ItemTypeCustomDeserializer())
        .create()
}
