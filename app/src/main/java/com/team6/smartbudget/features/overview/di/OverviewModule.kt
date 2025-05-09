package com.team6.smartbudget.features.overview.di

import com.team6.smartbudget.features.overview.data.di.OverviewDataModule
import com.team6.smartbudget.features.overview.presentation.di.OverviewPresentationModule
import dagger.Module

@Module(
    includes = [
        OverviewDataModule::class,
        OverviewPresentationModule::class,
    ],
)
interface OverviewModule
