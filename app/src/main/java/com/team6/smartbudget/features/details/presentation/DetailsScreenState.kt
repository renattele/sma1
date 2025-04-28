package com.team6.smartbudget.features.details.presentation

import com.team6.smartbudget.features.details.domain.TrackDetailsEntity

sealed class DetailsScreenState {
    data object Loading : DetailsScreenState()

    data class Success(val track: TrackDetailsEntity) : DetailsScreenState()

    data class Failure(val reason: FailureReason) : DetailsScreenState()

    sealed interface FailureReason {
        data object NoInternet : FailureReason

        data object InvalidApiKey : FailureReason

        data object TrackNotFound : FailureReason
    }
}

sealed interface DetailsScreenEvent {
    data object GoBack : DetailsScreenEvent
}
