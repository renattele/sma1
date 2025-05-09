package com.team6.smartbudget.features.overview.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteTracksResponseDto(
    val tracks: RemoteTracksTrackDto,
)

@Serializable
data class RemoteTracksTrackDto(
    @SerialName("track")
    val track: List<RemoteTrackDto>,
)

@Serializable
data class RemoteTrackDto(
    val name: String,
    val duration: String,
    val playcount: String,
    val listeners: String,
    val mbid: String,
    val url: String,
    val artist: RemoteTrackArtistDto,
    val image: List<RemoteTrackImageDto>,
)

@Serializable
data class RemoteTrackArtistDto(
    val name: String,
    val mbid: String,
    val url: String,
)

@Serializable
data class RemoteTrackImageDto(
    @SerialName("#text")
    val text: String,
    val size: String,
)
