package com.team6.smartbudget.features.details.presentation.di

import androidx.lifecycle.ViewModel
import com.team6.smartbudget.core.presentation.viewmodel.ViewModelKey
import com.team6.smartbudget.features.details.presentation.DetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface DetailsPresentationModule {
    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    fun bindDetailsViewModel(viewModel: DetailsViewModel): ViewModel
}
