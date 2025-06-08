package com.team6.smartbudget.features.onboarding.di

import androidx.lifecycle.ViewModel
import com.team6.smartbudget.core.presentation.viewmodel.ViewModelKey
import com.team6.smartbudget.features.onboarding.OnboardingViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface OnboardingModule {
    @Binds
    @IntoMap
    @ViewModelKey(OnboardingViewModel::class)
    fun bindOnboardingViewModel(viewModel: OnboardingViewModel): ViewModel
}