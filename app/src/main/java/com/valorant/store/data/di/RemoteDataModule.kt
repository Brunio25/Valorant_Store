package com.valorant.store.data.di

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.addDeserializer
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.valorant.store.data.config.interceptors.AuthInterceptor
import com.valorant.store.data.config.interceptors.StorefrontUrlInterceptor
import com.valorant.store.data.config.serde.ItemType
import com.valorant.store.data.config.serde.ItemTypeCustomDeserializer
import com.valorant.store.data.config.serde.LocalDateTimeCustomDeserializer
import com.valorant.store.data.datasource.Client
import com.valorant.store.data.datasource.entitlement.service.EntitlementClient
import com.valorant.store.data.datasource.storefront.service.StorefrontClient
import com.valorant.store.data.datasource.user.service.UserClient
import com.valorant.store.data.datasource.valInfo.service.ValInfoClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.time.LocalDateTime
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {

    @Provides
    @Named("entitlement_base_url")
    fun provideEntitlementBaseUrl(): String = "https://entitlements.auth.riotgames.com"

    @Provides
    @Named("user_base_url")
    fun provideUserBaseUrl(): String = "https://auth.riotgames.com"

    @Provides
    @Named("storefront_base_url")
    fun provideStorefrontBaseUrl(): String = "https://pd.placeholder.a.pvp.net"

    @Provides
    @Named("val_info_base_url")
    fun provideValInfoBaseUrl(): String = "https://valorant-api.com/"

    @Provides
    @Singleton
    fun provideEntitlementRetrofit(
        @Named("entitlement_base_url") baseUrl: String,
        @AuthHttpClient httpClient: OkHttpClient
    ): EntitlementClient = createRetrofitClient(baseUrl, httpClient)

    @Provides
    @Singleton
    fun provideUserRetrofit(
        @Named("user_base_url") baseUrl: String,
        @AuthHttpClient httpClient: OkHttpClient
    ): UserClient = createRetrofitClient(baseUrl, httpClient)

    @Provides
    @Singleton
    fun provideStorefrontRetrofit(
        @Named("storefront_base_url") baseUrl: String,
        @AuthHttpClient httpClient: OkHttpClient
    ): StorefrontClient = createRetrofitClient(
        baseUrl = baseUrl,
        httpClient = httpClient.newBuilder()
            .addInterceptor(StorefrontUrlInterceptor)
            .build()
    )

    @Provides
    @Singleton
    fun provideValInfoRetrofit(
        @Named("val_info_base_url") baseUrl: String,
        httpClient: OkHttpClient
    ): ValInfoClient = createRetrofitClient(baseUrl, httpClient)

    @Provides
    @Singleton
    @AuthHttpClient
    fun provideOauthOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient()

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AuthHttpClient
}

private inline fun <reified T : Client> createRetrofitClient(
    baseUrl: String,
    httpClient: OkHttpClient
): T = Retrofit.Builder()
    .register(baseUrl, httpClient)
    .build()
    .create(T::class.java)

private fun Retrofit.Builder.register(
    baseUrl: String,
    okHttpClient: OkHttpClient
): Retrofit.Builder = baseUrl(baseUrl)
    .client(okHttpClient)
    .jacksonConverterFactory()


private fun Retrofit.Builder.jacksonConverterFactory(): Retrofit.Builder = ObjectMapper()
    .registerKotlinModule()
    .registerSerdeModule()
    .let { JacksonConverterFactory.create(it) }
    .let { addConverterFactory(it) }

private fun ObjectMapper.registerSerdeModule(): ObjectMapper = SimpleModule()
    .addDeserializer(LocalDateTime::class, LocalDateTimeCustomDeserializer())
    .addDeserializer(ItemType::class, ItemTypeCustomDeserializer())
    .let { registerModule(it) }
