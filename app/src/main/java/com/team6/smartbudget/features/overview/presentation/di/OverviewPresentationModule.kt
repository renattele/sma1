package com.team6.smartbudget.features.overview.presentation.di

import androidx.lifecycle.ViewModel
import com.team6.smartbudget.core.presentation.viewmodel.ViewModelKey
import com.team6.smartbudget.features.overview.presentation.OverviewViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface OverviewPresentationModule {
    @Binds
    @IntoMap
    @ViewModelKey(OverviewViewModel::class)
    fun bindOverviewViewModel(viewModel: OverviewViewModel): ViewModel
}
