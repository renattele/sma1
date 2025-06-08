package com.team6.smartbudget.core.presentation.designsystem.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.team6.smartbudget.core.presentation.designsystem.theme.TPreviewTheme
import com.team6.smartbudget.core.presentation.designsystem.theme.TTheme

private val LinearProgressBarWidth = 200.dp
private val LinearProgressBarHeight = 8.dp

@Composable
fun TLinearProgressBar(
    progress: () -> Float,
    modifier: Modifier = Modifier,
    color: Color = TTheme.colorScheme.primary,
    trackColor: Color = TTheme.colorScheme.secondary,
) {
    Canvas(
        modifier = modifier
            .width(LinearProgressBarWidth)
            .height(LinearProgressBarHeight),
    ) {
        val width = size.width

        drawRoundRect(
            color = trackColor,
            cornerRadius = CornerRadius(width / 2),
        )
        drawRoundRect(
            color = color,
            size = size.copy(width = width * progress().coerceIn(0f, 1f)),
            cornerRadius = CornerRadius(width / 2),
        )
    }
}

private class ColorProvider :
    CollectionPreviewParameterProvider<@Composable () -> Color>(
        listOf(
            { TTheme.colorScheme.primary },
            { TTheme.colorScheme.error },
            { TTheme.colorScheme.success },
        ),
    )

@PreviewLightDark
@Composable
private fun TLinearProgressBarPreview(
    @PreviewParameter(ColorProvider::class) color: @Composable () -> Color,
) {
    TPreviewTheme {
        TLinearProgressBar(
            progress = { 0.5f },
            color = color(),
        )
    }
}
