package com.team6.smartbudget.features.graph.presentation

import androidx.lifecycle.ViewModel
import com.team6.smartbudget.features.graph.presentation.GraphScreenState.FailureReason
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class GraphViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(GraphScreenState())
    val state = _state.asStateFlow()

    fun handleEvent(event: GraphScreenEvent) {
        when (event) {
            is GraphScreenEvent.EditPoints -> editPoints(event.pointsString)
            is GraphScreenEvent.EditPointsCount -> editPointsCount(event.pointsString)
            GraphScreenEvent.Render -> render()
        }
    }

    private fun editPointsCount(pointsString: String) {
        val count = pointsString.toIntOrNull()
        if (count != null && count > 0) {
            _state.update {
                it.copy(
                    pointsCount = count,
                    failureReason = if (it.points?.size != count) FailureReason.InvalidActualPointsCount else null
                )
            }
        } else {
            _state.update {
                it.copy(
                    pointsCount = null,
                    failureReason = FailureReason.InvalidPointsCount
                )
            }
        }
    }

    private fun editPoints(pointsString: String) {
        val points = pointsString.split(",").map { it.toIntOrNull() }
        val pointsFiltered = points.filterNotNull()
        if (points.any { it == null } || pointsFiltered.any { it < 0 }) {
            _state.update {
                it.copy(
                    points = null,
                    failureReason = FailureReason.InvalidPoints
                )
            }
            return
        }

        if (pointsFiltered.size != _state.value.pointsCount) {
            _state.update {
                it.copy(
                    points = pointsFiltered.toImmutableList(),
                    failureReason = FailureReason.InvalidActualPointsCount
                )
            }
        } else {
            _state.update {
                it.copy(
                    points = pointsFiltered.toImmutableList(),
                    failureReason = null
                )
            }
        }
    }

    private fun render() {
        _state.update { it.copy(shouldRender = !it.shouldRender) }
    }
}