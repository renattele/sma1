package com.team6.smartbudget.features.details.data

import com.team6.smartbudget.core.domain.exception.InvalidApiKeyException
import com.team6.smartbudget.core.domain.network.LastFmResponse
import com.team6.smartbudget.core.util.Date
import com.team6.smartbudget.core.util.hasPassed
import com.team6.smartbudget.features.details.data.local.LocalTrackDetailsDataSource
import com.team6.smartbudget.features.details.data.local.mapper.LocalTrackDetailsMapper
import com.team6.smartbudget.features.details.data.remote.RemoteTrackDetailsDataSource
import com.team6.smartbudget.features.details.data.remote.mapper.RemoteTrackDetailsMapper
import com.team6.smartbudget.features.details.domain.TrackDetailsEntity
import com.team6.smartbudget.features.details.domain.TrackDetailsRepository
import com.team6.smartbudget.features.details.domain.exception.TrackNotFoundException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

private val DEFAULT_CACHE_MAX_AGE = 50.seconds
private const val DEFAULT_CACHE_MAX_ACCESS_COUNT = 3

class TrackDetailsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteTrackDetailsDataSource,
    private val remoteMapper: RemoteTrackDetailsMapper,
    private val localMapper: LocalTrackDetailsMapper,
    private val localDataSource: LocalTrackDetailsDataSource,
    private val coroutineScope: CoroutineScope
) : TrackDetailsRepository {

    private val recentRequests = mutableListOf<String>()
    private val maxTrackedRequests = DEFAULT_CACHE_MAX_ACCESS_COUNT + 1

    override suspend fun getTrackDetails(
        artist: String,
        title: String,
    ): Result<TrackDetailsEntity> {
        val cacheKey = "$artist:$title"

        val localTrackDetails = localDataSource.getByTitleAndArtist(title, artist)

        if (localTrackDetails != null &&
            !Date(localTrackDetails.updatedAt).hasPassed(DEFAULT_CACHE_MAX_AGE) &&
            shouldUseCache(cacheKey)
        ) {
            return Result.success(localMapper.toDomain(localTrackDetails))
        }

        coroutineScope.launch {
            if (localTrackDetails != null) localDataSource.delete(localTrackDetails)
        }

        val remoteTrack = loadTrackDetailsFromNetwork(artist, title)

        coroutineScope.launch {
            remoteTrack.onSuccess {
                localDataSource.upsert(localMapper.toDto(it))
                recordCacheUsage(cacheKey)
            }
        }

        return remoteTrack
    }


    private fun shouldUseCache(key: String): Boolean {
        synchronized(recentRequests) {
            val lastPosition = recentRequests.lastIndexOf(key)
            if (lastPosition == -1) return true

            val differentRequestsSinceLastUse = recentRequests
                .subList(lastPosition + 1, recentRequests.size)
                .distinct()
                .count()

            return differentRequestsSinceLastUse < DEFAULT_CACHE_MAX_ACCESS_COUNT
        }
    }

    private fun recordCacheUsage(key: String) {
        synchronized(recentRequests) {
            recentRequests.add(key)
            if (recentRequests.size > maxTrackedRequests) {
                recentRequests.removeAt(0)
            }
        }
    }

    private suspend fun loadTrackDetailsFromNetwork(
        artist: String,
        title: String
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
