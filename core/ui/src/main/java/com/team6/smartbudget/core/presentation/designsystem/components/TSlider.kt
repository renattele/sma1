package com.team6.smartbudget.core.presentation.designsystem.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.team6.smartbudget.core.presentation.designsystem.theme.TPreviewTheme
import com.team6.smartbudget.core.presentation.designsystem.theme.TTheme

private val TrackHeight = 2.dp
private val ThumbWidth = 18.dp
private val ThumbHeight = 18.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    onValueChangeFinished: (() -> Unit)? = null,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    color: Color = TTheme.colorScheme.primary,
    trackColor: Color = TTheme.colorScheme.secondary,
) {
    val colors = SliderDefaults.colors(
        thumbColor = color,
        activeTickColor = color,
        inactiveTickColor = color,
        activeTrackColor = color,
        inactiveTrackColor = trackColor,
    )
    Slider(
        value,
        onValueChange = onValueChange,
        modifier,
        valueRange = valueRange,
        colors = colors,
        onValueChangeFinished = onValueChangeFinished,
        track = { state ->
            SliderDefaults.Track(
                state,
                modifier = Modifier.height(TrackHeight),
                colors = colors,
                drawStopIndicator = {},
                drawTick = { offset, color ->
                },
                thumbTrackGapSize = 0.dp,
            )
        },
        thumb = {
            SliderDefaults.Thumb(
                remember { MutableInteractionSource() },
                modifier = Modifier,
                colors = colors,
                thumbSize = DpSize(ThumbWidth, ThumbHeight),
            )
        },
    )
}

private class TSliderColorProvider :
    CollectionPreviewParameterProvider<@Composable () -> Color>(
        listOf(
            { TTheme.colorScheme.primary },
            { TTheme.colorScheme.error },
            { TTheme.colorScheme.success },
        ),
    )

private const val PreviewProgress = 0.5f

@PreviewLightDark
@Composable
private fun TSliderPreview(
    @PreviewParameter(TSliderColorProvider::class) color: @Composable () -> Color,
) {
    TPreviewTheme {
        var value by remember { mutableFloatStateOf(PreviewProgress) }
        TSlider(
            value = value,
            onValueChange = { value = it },
            color = color(),
        )
    }
}
