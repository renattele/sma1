package com.team6.smartbudget.features.overview.data.remote.mapper

import com.team6.smartbudget.core.domain.TrackSummaryEntity
import com.team6.smartbudget.features.overview.data.remote.RemoteTracksResponseDto
import javax.inject.Inject

class RemoteTrackResponseMapperImpl @Inject constructor() : RemoteTrackResponseMapper {
    override fun toDomain(response: RemoteTracksResponseDto): List<TrackSummaryEntity> =
        response.tracks.track.map { track ->
            TrackSummaryEntity(
                title = track.name,
                artist = track.artist.name,
                thumbnailUrl = track.image[3].text,
            )
        }
}
