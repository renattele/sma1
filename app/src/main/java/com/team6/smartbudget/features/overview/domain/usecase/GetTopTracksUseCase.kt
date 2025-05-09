package com.team6.smartbudget.features.overview.domain.usecase

import com.team6.smartbudget.core.domain.TrackSummaryEntity
import com.team6.smartbudget.features.overview.domain.TopTracksRepository
import javax.inject.Inject

class GetTopTracksUseCase @Inject constructor(
    private val repository: TopTracksRepository,
) : suspend () -> Result<List<TrackSummaryEntity>> {
    override suspend fun invoke(): Result<List<TrackSummaryEntity>> {
        return repository.getTopTracks()
    }
}
