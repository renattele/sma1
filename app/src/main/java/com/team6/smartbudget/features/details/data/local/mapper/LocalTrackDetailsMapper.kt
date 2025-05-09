package com.team6.smartbudget.features.details.data.local.mapper

import com.team6.smartbudget.features.details.data.local.LocalTrackDetailsDto
import com.team6.smartbudget.features.details.domain.TrackDetailsEntity

interface LocalTrackDetailsMapper {
    fun toDto(entity: TrackDetailsEntity): LocalTrackDetailsDto
    fun toDomain(dto: LocalTrackDetailsDto): TrackDetailsEntity
}