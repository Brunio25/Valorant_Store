package com.valorant.store.data.di

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object UtilModule {
    @Provides
    @DefaultObjectMapper
    fun provideObjectMapper(): ObjectMapper = ObjectMapper().registerKotlinModule()

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class DefaultObjectMapper
}
