package com.team6.smartbudget.features.details.presentation

import androidx.lifecycle.viewModelScope
import com.team6.smartbudget.core.domain.exception.InvalidApiKeyException
import com.team6.smartbudget.core.presentation.viewmodel.ProvidedViewModel
import com.team6.smartbudget.features.details.domain.exception.TrackNotFoundException
import com.team6.smartbudget.features.details.domain.usecase.GetTrackDetailsUseCase
import com.team6.smartbudget.features.details.presentation.DetailsScreenState.FailureReason
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val getTrackDetailsUseCase: GetTrackDetailsUseCase,
) : ProvidedViewModel<DetailsViewModel.Dependencies>() {
    data class Dependencies(
        val artist: String,
        val title: String,
        val onGoBack: () -> Unit,
    )

    init {
        viewModelScope.launch {
            getTrackDetailsUseCase(
                dependencies().artist,
                dependencies().title,
            ).onSuccess { track ->
                _state.update { DetailsScreenState.Success(track) }
            }.onFailure { ex ->
                val reason = when (ex) {
                    is TrackNotFoundException -> FailureReason.TrackNotFound
                    is InvalidApiKeyException -> FailureReason.InvalidApiKey
                    else -> FailureReason.NoInternet
                }
                _state.update { DetailsScreenState.Failure(reason) }
            }
        }
    }

    private val _state = MutableStateFlow<DetailsScreenState>(DetailsScreenState.Loading)
    val state = _state.asStateFlow()

    fun handleEvent(event: DetailsScreenEvent) {
        viewModelScope.launch {
            when (event) {
                DetailsScreenEvent.GoBack -> dependencies().onGoBack()
            }
        }
    }
}
