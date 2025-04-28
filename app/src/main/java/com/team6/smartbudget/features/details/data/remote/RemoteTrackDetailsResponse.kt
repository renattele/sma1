package com.team6.smartbudget.features.details.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteTrackDetailsResponse(
    val track: RemoteTrackDetailsTrack,
)

@Serializable
data class RemoteTrackDetailsTrack(
    val name: String,
    val url: String,
    val duration: String,
    val listeners: String,
    val playcount: String,
    val artist: RemoteTrackDetailsTrackArtist,
    val album: RemoteTrackDetailsTrackAlbum? = null,
    val wiki: RemoteTrackDetailsTrackWiki,
)

@Serializable
data class RemoteTrackDetailsTrackArtist(
    val name: String,
    val url: String,
)

@Serializable
data class RemoteTrackDetailsTrackAlbum(
    val artist: String,
    val title: String,
    val url: String,
    val image: List<RemoteTrackDetailsTrackAlbumImage>,
)

@Serializable
data class RemoteTrackDetailsTrackAlbumImage(
    @SerialName("#text")
    val text: String,
    val size: String,
)

@Serializable
data class RemoteTrackDetailsTrackWiki(
    val published: String,
    val summary: String,
    val content: String,
)
