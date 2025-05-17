package com.team6.smartbudget.features.overview.presentation

import com.team6.smartbudget.core.domain.TrackSummaryEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

sealed class OverviewScreenState {
    data class Success(
        val tracks: ImmutableList<TrackSummaryEntity> = persistentListOf(),
    ) : OverviewScreenState()

    data object Loading : OverviewScreenState()

    data class Failure(val reason: FailureReason) : OverviewScreenState()

    sealed interface FailureReason {
        data object InvalidApiKey : FailureReason

        data object NoInternet : FailureReason
    }
}

sealed interface OverviewScreenEvent {
    data class GoToTrack(val track: TrackSummaryEntity) : OverviewScreenEvent

    data object GoBack : OverviewScreenEvent
}
