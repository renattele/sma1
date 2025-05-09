package com.team6.smartbudget.features.onboarding

sealed interface OnboardingScreenEvent {
    data class EditApiKey(val apiKey: String) : OnboardingScreenEvent
    data object Next : OnboardingScreenEvent
}