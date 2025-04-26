package com.team6.smartbudget.core.di

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import com.team6.smartbudget.core.data.AppSettings
import com.team6.smartbudget.core.data.UuidSerializer
import com.team6.smartbudget.core.data.serializableDataStore
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import javax.inject.Singleton
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Module(includes = [CoreProvidesModule::class, NetworkModule::class])
abstract class CoreModule

@Module
open class CoreProvidesModule {
    @Singleton
    @Provides
    open fun provideAppSettingsStore(
        context: Context,
        json: Json,
    ): DataStore<AppSettings> = context.serializableDataStore<AppSettings>(
        "settings.json",
        AppSettings(),
        json = json,
    )

    @OptIn(ExperimentalUuidApi::class)
    @Singleton
    @Provides
    open fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = true
        serializersModule = SerializersModule {
            contextual(Uuid::class) { UuidSerializer }
        }
    }

    @Provides
    @Singleton
    open fun provideCoroutineScope(): CoroutineScope = CoroutineScope(
        Dispatchers.IO + SupervisorJob() + CoroutineExceptionHandler { _, throwable ->
            Log.e("CoroutineExceptionHandler", throwable.message ?: "", throwable)
        },
    )
}
