package com.team6.smartbudget.features.overview.data

import com.team6.smartbudget.core.domain.TrackSummaryEntity
import com.team6.smartbudget.core.domain.exception.InvalidApiKeyException
import com.team6.smartbudget.core.domain.exception.NoInternetException
import com.team6.smartbudget.features.overview.data.remote.RemoteTopTracksDataSource
import com.team6.smartbudget.features.overview.data.remote.mapper.RemoteTrackResponseMapper
import com.team6.smartbudget.features.overview.domain.TopTracksRepository
import java.net.HttpURLConnection
import javax.inject.Inject

class TopTrackRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteTopTracksDataSource,
    private val remoteMapper: RemoteTrackResponseMapper,
) : TopTracksRepository {
    override suspend fun getTopTracks(): Result<List<TrackSummaryEntity>> {
        return runCatching {
            val response = remoteDataSource.getTopTracks()
            if (response.code() == HttpURLConnection.HTTP_FORBIDDEN) {
                throw InvalidApiKeyException()
            }

            val body = response.body()
            if (body == null || !response.isSuccessful) {
                throw NoInternetException()
            }
            remoteMapper.toDomain(body)
        }
    }
}
