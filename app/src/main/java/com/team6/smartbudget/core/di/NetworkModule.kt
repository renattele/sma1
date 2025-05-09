package com.team6.smartbudget.core.di

import com.team6.smartbudget.sma1.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import kotlinx.serialization.json.Json
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module(
    includes = [
        RetrofitInterceptorsModule::class,
        NetworkProvidesModule::class,
    ],
)
interface NetworkModule

@Module
open class NetworkProvidesModule {
    @Provides
    @ElementsIntoSet
    fun provideAdapters(): Set<CallAdapter.Factory> = setOf()

    @Singleton
    @Provides
    open fun certificatePinner(): CertificatePinner = CertificatePinner
        .Builder()
        .add(
            BuildConfig.API_HOSTNAME,
            BuildConfig.API_CERTIFICATE,
        ).build()

    @Singleton
    @Provides
    open fun okHttpClient(
        certificatePinner: CertificatePinner,
        interceptors: Set<@JvmSuppressWildcards Interceptor>,
    ): OkHttpClient = OkHttpClient
        .Builder()
        .certificatePinner(certificatePinner)
        .apply {
            interceptors.forEach {
                addInterceptor(it)
            }
        }.build()

    @Singleton
    @Provides
    open fun provideRetrofit(
        json: Json,
        okHttpClient: OkHttpClient,
        adapters: Set<@JvmSuppressWildcards CallAdapter.Factory>,
        interceptors: Set<@JvmSuppressWildcards Interceptor>,
    ): Retrofit = Retrofit
        .Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .client(okHttpClient)
        .apply {
            adapters.forEach {
                addCallAdapterFactory(it)
            }
        }.addConverterFactory(
            json.asConverterFactory(
                contentType = "application/json".toMediaType(),
            ),
        ).build()
}
