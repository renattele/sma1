package com.team6.smartbudget.features.details.di

import com.team6.smartbudget.features.details.data.di.DetailsDataModule
import com.team6.smartbudget.features.details.presentation.di.DetailsPresentationModule
import dagger.Module

@Module(
    includes = [
        DetailsDataModule::class,
        DetailsPresentationModule::class,
    ],
)
interface DetailsModule
