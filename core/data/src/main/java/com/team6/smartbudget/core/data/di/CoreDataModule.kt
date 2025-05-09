package com.team6.smartbudget.core.data.di

import androidx.datastore.core.DataStore
import com.team6.smartbudget.core.data.DataStoreLocalStore
import com.team6.smartbudget.core.data.local.LocalApiKeySettings
import com.team6.smartbudget.core.data.remote.RemoteApiKeyCheckDataSource
import com.team6.smartbudget.core.data.repository.ApiKeyRepositoryImpl
import com.team6.smartbudget.core.domain.LocalStore
import com.team6.smartbudget.core.domain.repository.ApiKeyRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.serialization.serializer
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [CoreDataProvidesModule::class])
interface CoreDataModule {
    @Binds
    @Singleton
    fun bindApiKeyRepository(impl: ApiKeyRepositoryImpl): ApiKeyRepository

    @Binds
    @Singleton
    fun bindLocalStoreFactory(factory: DataStoreLocalStore.Factory): LocalStore.Factory
}

@Module
open class CoreDataProvidesModule {
    @Provides
    @Singleton
    fun provideLocalApiKeySettingsLocalStore(factory: LocalStore.Factory) = factory.create(
        name = "api_key_settings",
        serializer = serializer(),
        defaultValue = LocalApiKeySettings(),
    )

    @Provides
    @Singleton
    fun provideRemoteApiKeyCheckDataSource(retrofit: Retrofit): RemoteApiKeyCheckDataSource =
        retrofit.create(RemoteApiKeyCheckDataSource::class.java)
}