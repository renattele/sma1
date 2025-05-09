package com.team6.smartbudget.shared.presentation

import kotlinx.serialization.Serializable

object Destinations {
    @Serializable
    data object Overview

    @Serializable
    data class TrackDetails(val artist: String, val title: String)
}
