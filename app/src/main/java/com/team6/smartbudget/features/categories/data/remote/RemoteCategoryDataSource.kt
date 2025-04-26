@file:OptIn(ExperimentalUuidApi::class)

package com.team6.smartbudget.features.categories.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface RemoteCategoryDataSource {
    @GET("/budget/categories")
    suspend fun getAll(): Response<List<RemoteCategoryDto>>

    @POST("/budget/categories")
    suspend fun insert(
        @Body categoryEntity: RemoteCategoryDto,
    ): Response<Unit>

    @DELETE("/budget/categories/{id}")
    suspend fun deleteById(
        @Path("id") id: Uuid,
    ): Response<Unit>
}
