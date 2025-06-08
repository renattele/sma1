package com.team6.smartbudget.core.presentation.designsystem.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class TSpacing(
    val xxs: Dp,
    val xs: Dp,
    val s: Dp,
    val m: Dp,
    val l: Dp,
    val xl: Dp,
)

fun defaultTSpacing() = TSpacing(
    xxs = 1.dp,
    xs = 4.dp,
    s = 8.dp,
    m = 16.dp,
    l = 24.dp,
    xl = 32.dp,
)

val LocalTSpacing = staticCompositionLocalOf { defaultTSpacing() }
