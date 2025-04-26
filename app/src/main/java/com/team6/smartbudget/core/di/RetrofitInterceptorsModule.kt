package com.team6.smartbudget.core.di

import com.team6.smartbudget.core.data.network.DomainInterceptor
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import okhttp3.Interceptor

@Module
interface RetrofitInterceptorsModule {
    @Binds
    @IntoSet
    fun bindDomainInterceptor(interceptor: DomainInterceptor): Interceptor
}
