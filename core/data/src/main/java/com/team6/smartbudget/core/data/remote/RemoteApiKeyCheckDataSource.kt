package com.team6.smartbudget.core.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteApiKeyCheckDataSource {
    @GET("/?method=auth.gettoken")
    suspend fun getToken(@Query("api_key") apiKey: String): Response<RemoteApiKeyCheckResponse>
}