package com.team6.smartbudget.features.graph.presentation.di

import androidx.lifecycle.ViewModel
import com.team6.smartbudget.core.presentation.viewmodel.ViewModelKey
import com.team6.smartbudget.features.graph.presentation.GraphViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface GraphPresentationModule {
    @Binds
    @ViewModelKey(GraphViewModel::class)
    @IntoMap
    fun bindGraphViewModel(viewModel: GraphViewModel): ViewModel
}