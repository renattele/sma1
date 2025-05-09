package com.team6.smartbudget.features.details.domain.usecase

import com.team6.smartbudget.features.details.domain.TrackDetailsEntity
import com.team6.smartbudget.features.details.domain.TrackDetailsRepository
import javax.inject.Inject

class GetTrackDetailsUseCase @Inject constructor(
    private val trackDetailsRepository: TrackDetailsRepository,
) : suspend (String, String) -> Result<TrackDetailsEntity> {
    override suspend fun invoke(
        artist: String,
        title: String,
    ): Result<TrackDetailsEntity> {
        return trackDetailsRepository.getTrackDetails(artist, title)
    }
}
