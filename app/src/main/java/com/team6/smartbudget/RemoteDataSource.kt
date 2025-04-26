package com.team6.smartbudget

import retrofit2.http.GET

interface RemoteDataSource {
    @GET("/")
    suspend fun getData(): String
}
