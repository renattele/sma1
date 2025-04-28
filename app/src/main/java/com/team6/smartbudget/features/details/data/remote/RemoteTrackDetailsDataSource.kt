package com.team6.smartbudget.features.details.data.remote

import com.team6.smartbudget.core.domain.network.LastFmResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteTrackDetailsDataSource {
    @GET("/?method=track.getInfo")
    suspend fun getTrackDetails(
        @Query("artist") artist: String,
        @Query("track") title: String,
    ): Response<LastFmResponse<RemoteTrackDetailsResponse>>
}
