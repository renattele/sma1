package com.team6.smartbudget.features.details.domain

interface TrackDetailsRepository {
    suspend fun getTrackDetails(
        artist: String,
        title: String,
    ): Result<TrackDetailsEntity>
}
