package com.team6.smartbudget.features.details.data

import com.team6.smartbudget.core.domain.exception.InvalidApiKeyException
import com.team6.smartbudget.core.domain.network.LastFmResponse
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
import kotlin.time.Duration.Companion.minutes

private val DEFAULT_CACHE_DURATION = 5.minutes
private const val MAX_REQUESTS_BETWEEN_SAME_KEY = 3

class TrackDetailsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteTrackDetailsDataSource,
    private val remoteMapper: RemoteTrackDetailsMapper,
    private val localMapper: LocalTrackDetailsMapper,
    private val localDataSource: LocalTrackDetailsDataSource,
    private val coroutineScope: CoroutineScope
) : TrackDetailsRepository {

    private val cacheTimestamps = mutableMapOf<String, Long>()

    private val requestHistory = mutableListOf<String>()

    override suspend fun getTrackDetails(
        artist: String,
        title: String,
    ): Result<TrackDetailsEntity> {
        val cacheKey = "$artist:$title"

        val localTrackDetails = localDataSource.getByTitleAndArtist(title, artist)

        val shouldUseCache = synchronized(this) {
            val lastAccessTime = cacheTimestamps[cacheKey]

            if (lastAccessTime != null) {
                val timeSinceLastAccess = System.currentTimeMillis() - lastAccessTime
                val cacheNotExpired = timeSinceLastAccess < DEFAULT_CACHE_DURATION.inWholeMilliseconds

                // Check how many other unique requests have been made since last access
                val requestsSinceLastAccess = if (cacheKey in requestHistory) {
                    val lastIndex = requestHistory.lastIndexOf(cacheKey)
                    requestHistory.subList(lastIndex + 1, requestHistory.size).distinct().size
                } else {
                    Int.MAX_VALUE
                }

                // Use cache if it's not expired and there haven't been too many different requests
                cacheNotExpired && requestsSinceLastAccess < MAX_REQUESTS_BETWEEN_SAME_KEY
            } else {
                false
            }
        }

        // Update request history
        synchronized(this) {
            requestHistory.add(cacheKey)
            cacheTimestamps[cacheKey] = System.currentTimeMillis()
        }

        // Return cached result if conditions are met
        if (shouldUseCache && localTrackDetails != null) {
            return Result.success(localMapper.toDomain(localTrackDetails))
        }

        // Otherwise fetch from network
        val remoteTrack = loadTrackDetailsFromNetwork(artist, title)

        // Update local cache
        coroutineScope.launch {
            remoteTrack.onSuccess {
                localDataSource.upsert(localMapper.toDto(it))
            }
        }

        return remoteTrack
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