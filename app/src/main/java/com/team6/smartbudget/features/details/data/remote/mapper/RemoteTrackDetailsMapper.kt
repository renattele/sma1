package com.team6.smartbudget.features.details.data.remote.mapper

import com.team6.smartbudget.features.details.data.remote.RemoteTrackDetailsResponse
import com.team6.smartbudget.features.details.domain.TrackDetailsEntity

interface RemoteTrackDetailsMapper {
    fun toDomain(track: RemoteTrackDetailsResponse): TrackDetailsEntity
}
