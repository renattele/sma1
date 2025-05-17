package com.team6.smartbudget.features.graph.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.team6.smartbudget.core.presentation.designsystem.theme.TPreviewTheme
import com.team6.smartbudget.core.presentation.designsystem.theme.TTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

private val GraphHeight = 160.dp
private val AxesLineWidth = 1.dp
private val GraphLineWidth = 2.dp
private const val WidthAxesCount = 11
private const val HeightAxesCount = 6

@Composable
fun Graph(
    points: ImmutableList<Int>,
    modifier: Modifier = Modifier,
    graphParams: GraphParams = GraphParams.default(),
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(GraphHeight)
    ) {
        drawGraph(
            points = points,
            params = graphParams,
        )
    }
}

data class GraphParams(
    val color: Color,
    val axesColor: Color,
    val axesLineWidth: Dp,
    val graphLineWidth: Dp,
    val widthAxesCount: Int,
    val heightAxesCount: Int,
) {
    companion object {
        @Composable
        fun default(): GraphParams {
            return GraphParams(
                color = TTheme.colorScheme.primary,
                axesColor = TTheme.colorScheme.outline,
                axesLineWidth = AxesLineWidth,
                widthAxesCount = WidthAxesCount,
                heightAxesCount = HeightAxesCount,
                graphLineWidth = GraphLineWidth
            )
        }
    }
}

private fun DrawScope.drawGraph(
    points: ImmutableList<Int>,
    params: GraphParams,
) {
    drawAxes(params)
    drawActualGraph(points, params)
}

private fun DrawScope.drawAxes(
    params: GraphParams,
) {
    repeat(params.widthAxesCount) { index ->
        drawLine(
            params.axesColor,
            Offset(index * size.width / (params.widthAxesCount - 1), 0f),
            Offset(index * size.width / (params.widthAxesCount - 1), size.height),
            params.axesLineWidth.toPx()
        )
    }
    repeat(params.heightAxesCount) { index ->
        drawLine(
            params.axesColor,
            Offset(0f, index * size.height / (params.heightAxesCount - 1)),
            Offset(size.width, index * size.height / (params.heightAxesCount - 1)),
            params.axesLineWidth.toPx()
        )
    }
}

private fun DrawScope.drawActualGraph(
    points: ImmutableList<Int>,
    params: GraphParams
) {
    if (points.isEmpty()) return
    val actualPoints = if (points.size == 1) List(2) { points[0] } else points
    val maxHeight = actualPoints.max()
    actualPoints.withIndex().zipWithNext { p1, p2 ->
        val point1WidthFraction = p1.index / (actualPoints.size.toFloat() - 1)
        val point1HeightFraction = 1f - p1.value / maxHeight.toFloat()
        val point2WidthFraction = p2.index / (actualPoints.size.toFloat() - 1)
        val point2HeightFraction = 1f - p2.value / maxHeight.toFloat()

        val xOffset = Offset(
            point1WidthFraction * size.width,
            point1HeightFraction * size.height
        )
        val yOffset = Offset(
            point2WidthFraction * size.width,
            point2HeightFraction * size.height
        )
        drawLine(
            params.color,
            xOffset,
            yOffset,
            params.graphLineWidth.toPx()
        )
        drawPath(
            Path().apply {
                moveTo(xOffset.x, xOffset.y)
                lineTo(yOffset.x, yOffset.y)
                lineTo(yOffset.x, size.height)
                lineTo(xOffset.x, size.height)
                close()
            },
            brush = Brush.verticalGradient(
                listOf(params.color, Color.Transparent)
            )
        )
    }
}

private class GraphPointsProvider : CollectionPreviewParameterProvider<ImmutableList<Int>>(
    listOf(
        persistentListOf(),
        persistentListOf(10),
        persistentListOf(2, 1, 3),
        persistentListOf(100, 1, 1000)
    )
)

@PreviewLightDark
@Composable
private fun GraphPreview(@PreviewParameter(GraphPointsProvider::class) points: ImmutableList<Int>) {
    TPreviewTheme {
        Graph(points)
    }
}