package com.valorant.store.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.valorant.store.data.model.clientPlatform.ClientPlatformLocal
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    @Singleton
    @ValInfoDataStore
    fun provideValInfoDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = PreferenceDataStoreFactory.create(
        produceFile = {
            context.preferencesDataStoreFile("valorant_store_content_cache.db")
        },
        scope = CoroutineScope(Dispatchers.IO + SupervisorJob()) // TODO: Possibly Remove?
    )

    @Provides
    fun provideClientPlatformLocal(): ClientPlatformLocal =
        ClientPlatformLocal() // TODO: Tech Debt Improve this

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ValInfoDataStore
}
