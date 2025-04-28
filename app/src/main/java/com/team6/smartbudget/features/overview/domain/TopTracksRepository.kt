package com.team6.smartbudget.features.overview.domain

import com.team6.smartbudget.core.domain.TrackSummaryEntity

interface TopTracksRepository {
    suspend fun getTopTracks(): Result<List<TrackSummaryEntity>>
}
