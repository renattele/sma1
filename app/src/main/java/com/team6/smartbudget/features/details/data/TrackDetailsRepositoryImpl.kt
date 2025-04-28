package com.team6.smartbudget.features.details.data

import com.team6.smartbudget.core.domain.exception.InvalidApiKeyException
import com.team6.smartbudget.core.domain.network.LastFmResponse
import com.team6.smartbudget.features.details.data.remote.RemoteTrackDetailsDataSource
import com.team6.smartbudget.features.details.data.remote.mapper.RemoteTrackDetailsMapper
import com.team6.smartbudget.features.details.domain.TrackDetailsEntity
import com.team6.smartbudget.features.details.domain.TrackDetailsRepository
import com.team6.smartbudget.features.details.domain.exception.TrackNotFoundException
import java.net.HttpURLConnection
import javax.inject.Inject

class TrackDetailsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteTrackDetailsDataSource,
    private val remoteMapper: RemoteTrackDetailsMapper,
) : TrackDetailsRepository {
    override suspend fun getTrackDetails(
        artist: String,
        title: String,
    ): Result<TrackDetailsEntity> = runCatching body@{
        val response = remoteDataSource.getTrackDetails(artist, title)
        if (response.code() == HttpURLConnection.HTTP_FORBIDDEN) {
            throw InvalidApiKeyException()
        }
        val body = response.body()
        require(body != null) { "Body not found, response: $response" }
        if (body is LastFmResponse.Success) {
            return@body remoteMapper.toDomain(body.data)
        } else {
            throw TrackNotFoundException()
        }
    }.onFailure { it.printStackTrace() }
}
