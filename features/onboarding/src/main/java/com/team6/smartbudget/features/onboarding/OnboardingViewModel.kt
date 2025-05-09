package com.team6.smartbudget.features.onboarding

import com.team6.smartbudget.core.presentation.viewmodel.ProvidedViewModel
import javax.inject.Inject

class OnboardingViewModel @Inject constructor() :
    ProvidedViewModel<OnboardingViewModel.Dependencies>() {
    data class Dependencies(
        val onNext: () -> Unit,
    )

}