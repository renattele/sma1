package com.renattele.sma1.presentation.util

import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue

val SwipeToDismissBoxState.progressToSwipe: Float
    get() = when {
        targetValue == currentValue &&
                targetValue == SwipeToDismissBoxValue.StartToEnd -> 1f
        targetValue == currentValue &&
                targetValue == SwipeToDismissBoxValue.Settled -> 0f
        else -> progress
    }