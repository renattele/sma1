package com.team6.smartbudget.core.presentation.util

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.platform.LocalDensity

fun Modifier.shimmer(
    enabled: Boolean = true,
    shape: Shape = CircleShape,
    durationMillis: Int = 1000,
): Modifier = composed {
    val transition = rememberInfiniteTransition(label = "")

    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 500f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart,
        ),
        label = "",
    )
    val animatedAlpha = animateFloatAsState(
        if (enabled) 1f else 0f,
        tween(durationMillis, easing = LinearEasing),
    )
    val density = LocalDensity.current

    return@composed Modifier.animateContentSize().drawWithContent {
        val rect = size.toRect()
        val basePaint = Paint().apply {
            alpha = 1f - animatedAlpha.value
        }
        drawContext.canvas.saveLayer(rect, basePaint)
        drawContent()
        drawContext.canvas.restore()

        val drawPaint = Paint().apply {
            alpha = animatedAlpha.value
        }
        drawContext.canvas.saveLayer(rect, drawPaint)
        val value = translateAnimation.value
        drawOutline(
            shape.createOutline(size, layoutDirection, density),
            brush = Brush.linearGradient(
                colors = listOf(
                    Color.LightGray.copy(alpha = 0.2f),
                    Color.LightGray.copy(alpha = 1.0f),
                    Color.LightGray.copy(alpha = 0.2f),
                ).map { it.copy(alpha = it.alpha * animatedAlpha.value) },
                start = Offset(x = value, y = value),
                end = Offset(x = value + 100f, y = value + 100f),
            ),
        )
        drawContext.canvas.restore()
    }
}
