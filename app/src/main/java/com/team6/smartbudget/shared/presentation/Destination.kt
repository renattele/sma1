package com.team6.smartbudget.shared.presentation

import kotlinx.serialization.Serializable

sealed interface Destination {
    @Serializable
    data object Overview : Destination

    @Serializable
    data class TrackDetails(val artist: String, val title: String) : Destination

    @Serializable
    data object Onboarding : Destination

    @Serializable
    data object Graph: Destination
}
