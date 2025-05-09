package com.team6.smartbudget.core.di

import com.team6.smartbudget.core.data.network.ApiKeyInterceptor
import com.team6.smartbudget.core.data.network.DomainInterceptor
import com.team6.smartbudget.core.data.network.LastFmInterceptor
import com.team6.smartbudget.sma1.BuildConfig
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import javax.inject.Singleton

@Module(includes = [RetrofitInterceptorsProvidesModule::class])
interface RetrofitInterceptorsModule {
    @Binds
    @Singleton
    @IntoSet
    fun bindDomainInterceptor(interceptor: DomainInterceptor): Interceptor

    @Binds
    @Singleton
    @IntoSet
    fun bindLastFmInterceptor(interceptor: LastFmInterceptor): Interceptor

    @Binds
    @Singleton
    @IntoSet
    fun bindApiKeyInterceptor(interceptor: ApiKeyInterceptor): Interceptor
}

@Module
open class RetrofitInterceptorsProvidesModule
