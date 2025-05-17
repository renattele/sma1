package com.team6.smartbudget.features.details.domain

import com.team6.smartbudget.core.domain.DataSourceType

data class TrackDetailsEntity(
    val title: String,
    val artist: String,
    val thumbnailUrl: String?,
    val published: String,
    val summary: String,
    val sourceType: DataSourceType
)
