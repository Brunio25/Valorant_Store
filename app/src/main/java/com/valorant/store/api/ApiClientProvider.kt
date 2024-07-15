package com.valorant.store.api

import android.util.Log
import com.google.gson.GsonBuilder
import com.valorant.store.api.interceptors.AuthInterceptor
import com.valorant.store.api.util.LocalDateTimeCustomDeserializer
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
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
            .addConverterFactory(GsonConverterFactory.create(createGson()))
            .client(OkHttpClient.Builder()
                //.addInterceptor(CustomLoggingInterceptor())
                .also {
                    if (includeAuthInterceptor) {
                        it.addInterceptor(AuthInterceptor)
                    }
                }.build())
            .build()

    private fun createGson() = GsonBuilder().registerTypeAdapter(
            LocalDateTime::class.java,
            LocalDateTimeCustomDeserializer()
        ).create()
}

class CustomLoggingInterceptor : Interceptor {
    private val logger = HttpLoggingInterceptor.Logger { message ->
        // Customize your logging mechanism here
        Log.d("HTTP", message)
    }
    private val loggingInterceptor = HttpLoggingInterceptor(logger).apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        // Log request
        val request = chain.request()
        loggingInterceptor.intercept(chain)

        // Proceed with the request
        val response = chain.proceed(request)

        // Log response
        loggingInterceptor.intercept(chain)

        return response
    }
}
