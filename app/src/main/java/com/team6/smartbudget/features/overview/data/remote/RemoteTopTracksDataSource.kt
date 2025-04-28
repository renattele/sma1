package com.team6.smartbudget.features.overview.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface RemoteTopTracksDataSource {
    @GET("/?method=chart.gettoptracks")
    suspend fun getTopTracks(): Response<RemoteTracksResponseDto>
}
