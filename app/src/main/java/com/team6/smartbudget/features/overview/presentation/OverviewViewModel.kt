package com.team6.smartbudget.features.overview.presentation

import androidx.lifecycle.viewModelScope
import com.team6.smartbudget.core.domain.TrackSummaryEntity
import com.team6.smartbudget.core.domain.exception.InvalidApiKeyException
import com.team6.smartbudget.core.presentation.viewmodel.ProvidedViewModel
import com.team6.smartbudget.features.overview.domain.TopTracksRepository
import com.team6.smartbudget.features.overview.presentation.OverviewScreenState.FailureReason
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class OverviewViewModel @Inject constructor(
    private val topTrackRepository: TopTracksRepository,
) : ProvidedViewModel<OverviewViewModel.Dependencies>() {
    data class Dependencies(
        val onGoToTrack: (TrackSummaryEntity) -> Unit,
        val onGoBack: () -> Unit,
    )

    init {
        viewModelScope.launch {
            topTrackRepository.getTopTracks().onSuccess { tracks ->
                _state.update { OverviewScreenState.Success(tracks.toImmutableList()) }
            }.onFailure { error ->
                val reason = when (error) {
                    is InvalidApiKeyException -> FailureReason.InvalidApiKey
                    else -> FailureReason.NoInternet
                }
                _state.update { OverviewScreenState.Failure(reason) }
            }
        }
    }

    private val _state = MutableStateFlow<OverviewScreenState>(OverviewScreenState.Loading)

    val state = _state.asStateFlow()

    fun handleEvent(event: OverviewScreenEvent) {
        viewModelScope.launch {
            when (event) {
                is OverviewScreenEvent.GoToTrack -> dependencies().onGoToTrack(
                    event.track,
                )

                OverviewScreenEvent.GoBack -> dependencies().onGoBack()
            }
        }
    }
}
