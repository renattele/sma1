package com.team6.smartbudget.features.categories.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface RemoteCategoryAnalyticDataSource {
    @GET("/analytic/categories")
    suspend fun getAll(): Response<List<RemoteCategoryAnalyticDto>>
}
