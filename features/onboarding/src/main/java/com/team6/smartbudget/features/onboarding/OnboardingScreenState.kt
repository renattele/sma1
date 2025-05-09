package com.team6.smartbudget.features.onboarding

data class OnboardingScreenState(
    val isLoading: Boolean = false,
    val apiKey: String = "",
    val failureReason: FailureReason? = null,
) {
    sealed interface FailureReason {
        object InvalidApiKey : FailureReason
        object NetworkError : FailureReason
        object UnknownError : FailureReason
    }
}