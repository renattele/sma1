package com.team6.smartbudget.features.graph.presentation

import kotlinx.collections.immutable.ImmutableList

data class GraphScreenState(
    val pointsCount: Int? = null,
    val points: ImmutableList<Int>? = null,
    val shouldRender: Boolean = false,
    val failureReason: FailureReason? = null,
) {
    sealed interface FailureReason {
        data object InvalidPointsCount: FailureReason
        data object InvalidPoints: FailureReason
        data object InvalidActualPointsCount: FailureReason
    }
}