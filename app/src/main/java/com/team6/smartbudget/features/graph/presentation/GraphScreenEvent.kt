package com.team6.smartbudget.features.graph.presentation

sealed interface GraphScreenEvent {
    data class EditPointsCount(val pointsString: String) : GraphScreenEvent
    data class EditPoints(val pointsString: String) : GraphScreenEvent
    data object Render: GraphScreenEvent
}