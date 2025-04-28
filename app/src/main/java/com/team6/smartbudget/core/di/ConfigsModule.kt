package com.team6.smartbudget.core.di

import com.team6.smartbudget.core.domain.ApplicationConfig
import com.team6.smartbudget.core.presentation.config.CoilImageLoaderConfig
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
interface ConfigsModule {
    @Binds
    @IntoSet
    @Singleton
    fun bindCoilImageLoaderConfig(config: CoilImageLoaderConfig): ApplicationConfig
}
