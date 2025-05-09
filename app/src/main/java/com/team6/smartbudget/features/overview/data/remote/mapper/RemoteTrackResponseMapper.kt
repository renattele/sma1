package com.team6.smartbudget.features.overview.data.remote.mapper

import com.team6.smartbudget.core.domain.TrackSummaryEntity
import com.team6.smartbudget.features.overview.data.remote.RemoteTracksResponseDto

interface RemoteTrackResponseMapper {
    fun toDomain(response: RemoteTracksResponseDto): List<TrackSummaryEntity>
}
