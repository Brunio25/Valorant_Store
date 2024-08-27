package com.valorant.store.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.addDeserializer
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.valorant.store.api.interceptors.AuthInterceptor
import com.valorant.store.api.config.ItemType
import com.valorant.store.api.config.ItemTypeCustomDeserializer
import com.valorant.store.api.config.LocalDateTimeCustomDeserializer
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
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
            .jacksonConverterFactory()
            .client(
                OkHttpClient.Builder()
                    .also {
                        if (includeAuthInterceptor) {
                            it.addInterceptor(AuthInterceptor)
                        }
                    }.build()
            ).build()

    private fun Retrofit.Builder.jacksonConverterFactory() = ObjectMapper()
        .registerKotlinModule()
        .registerSerdeModule()
        .let { JacksonConverterFactory.create(it) }
        .let { addConverterFactory(it) }

    private fun ObjectMapper.registerSerdeModule() = SimpleModule()
        .addDeserializer(LocalDateTime::class, LocalDateTimeCustomDeserializer())
        .addDeserializer(ItemType::class, ItemTypeCustomDeserializer())
        .let { registerModule(it) }
}
