package com.team6.smartbudget.core.di

import com.team6.smartbudget.core.domain.ApplicationConfig
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import javax.inject.Singleton

@Module
class ConfigsModule {
    @Provides
    @ElementsIntoSet
    @Singleton
    fun provideConfigs(): Set<ApplicationConfig> = emptySet()
}
