package com.team6.smartbudget.core.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun FullLineColumn(
    modifier: Modifier = Modifier,
    spacing: Dp = 0.dp,
    content: @Composable () -> Unit,
) {
    Layout(content = content, modifier = modifier) { measurables, constraints ->
        val modifiedConstraints = constraints.copy(
            minWidth = constraints.maxWidth,
            maxWidth = constraints.maxWidth,
        )

        val placeables = measurables.map { measurable ->
            measurable.measure(modifiedConstraints)
        }

        val spacingTotalHeight = ((placeables.size - 1) * spacing.roundToPx()).coerceAtLeast(0)
        val totalHeight = placeables.sumOf { it.height } + spacingTotalHeight

        layout(constraints.maxWidth, totalHeight) {
            var yPosition = 0
            placeables.forEach { placeable ->
                placeable.placeRelative(x = 0, y = yPosition)
                yPosition += placeable.height + spacing.roundToPx()
            }
        }
    }
}
