package com.team6.smartbudget.features.details.data.remote.mapper

import com.team6.smartbudget.core.domain.DataSourceType
import com.team6.smartbudget.features.details.data.remote.RemoteTrackDetailsResponse
import com.team6.smartbudget.features.details.domain.TrackDetailsEntity
import javax.inject.Inject

class RemoteTrackDetailsMapperImpl @Inject constructor() : RemoteTrackDetailsMapper {
    override fun toDomain(track: RemoteTrackDetailsResponse): TrackDetailsEntity =
        TrackDetailsEntity(
            title = track.track.name,
            artist = track.track.artist.name,
            thumbnailUrl = track.track.album?.image?.last()?.text,
            published = track.track.wiki.published,
            summary = track.track.wiki.content,
            sourceType = DataSourceType.Remote
        )
}
