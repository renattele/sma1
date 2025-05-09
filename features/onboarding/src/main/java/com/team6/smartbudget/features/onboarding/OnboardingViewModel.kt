package com.team6.smartbudget.features.onboarding

import androidx.lifecycle.viewModelScope
import com.team6.smartbudget.core.domain.exception.InvalidApiKeyException
import com.team6.smartbudget.core.domain.usecase.GetApiKeyUseCase
import com.team6.smartbudget.core.domain.usecase.UpdateApiKeyUseCase
import com.team6.smartbudget.core.presentation.viewmodel.ProvidedViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class OnboardingViewModel @Inject constructor(
    private val getApiKeyUseCase: GetApiKeyUseCase,
    private val updateApiKeyUseCase: UpdateApiKeyUseCase
) :
    ProvidedViewModel<OnboardingViewModel.Dependencies>() {
    data class Dependencies(
        val onNext: () -> Unit,
    )

    private val _state = MutableStateFlow(OnboardingScreenState())
    val state = _state.asStateFlow()

    fun handleEvent(event: OnboardingScreenEvent) {
        when (event) {
            OnboardingScreenEvent.DismissError -> {
                _state.update { it.copy(failureReason = null) }
            }

            is OnboardingScreenEvent.EditApiKey -> {
                _state.update { it.copy(apiKey = event.apiKey) }
            }

            OnboardingScreenEvent.Next -> tryNext()
        }
    }

    private fun tryNext() {
        viewModelScope.launch {
            updateApiKeyUseCase(state.value.apiKey)
                .onSuccess {
                    dependencies().onNext()
                }.onFailure { ex ->
                    ex.printStackTrace()
                    val error = when (ex) {
                        is InvalidApiKeyException -> {
                            OnboardingScreenState.FailureReason.InvalidApiKey
                        }
                        else -> {
                            OnboardingScreenState.FailureReason.UnknownError
                        }
                    }
                    _state.update { it.copy(failureReason = error) }
                }
        }
    }
}