package com.team6.smartbudget.core.presentation.designsystem.theme

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun ProvideTextStyle(
    textStyle: TextStyle,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalTextStyle provides textStyle, content = content)
}

@Composable
fun ProvideContentColor(
    color: Color,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalContentColor provides color, content = content)
}

@Composable
fun ProvideTextStyleAndColor(
    textStyle: TextStyle,
    color: Color,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalTextStyle provides textStyle,
        LocalContentColor provides color,
        content = content,
    )
}
