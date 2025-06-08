package com.team6.smartbudget.core.presentation.designsystem.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class TColorScheme(
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val background: Color,
    val onBackground: Color,
    val onBackgroundHigh: Color,
    val onBackgroundMedium: Color,
    val onBackgroundLow: Color,
    val error: Color,
    val onError: Color,
    val success: Color,
    val onSuccess: Color,
    val warning: Color,
    val onWarning: Color,
    val outline: Color,
)

// Needed to suppress because
// it's not necessary to have a single line for each Color
@Suppress("MagicNumber")
fun lightTColorScheme() = TColorScheme(
    primary = Color(0xFFF8D718),
    onPrimary = Color(0xFF000000),
    secondary = Color(0xFFF4F4F4),
    onSecondary = Color(0xFF000000),
    background = Color(0xFFFFFFFF),
    onBackground = Color(0xE5191C30),
    onBackgroundHigh = Color(0xE5191C30),
    onBackgroundMedium = Color(0xA51B1F3B),
    onBackgroundLow = Color(0x661B1F3B),
    error = Color(0xFFF45725),
    onError = Color(0xFFFFFFFF),
    success = Color(0xFF4AC99B),
    onSuccess = Color(0xFF000000),
    warning = Color(0xFFFFC700),
    onWarning = Color(0xFF000000),
    outline = Color(0xFFE0E0E0),
)

// Needed to suppress because
// it's not necessary to have a single line for each Color
@Suppress("MagicNumber")
fun darkTColorScheme() = TColorScheme(
    primary = Color(0xFFF8D718),
    onPrimary = Color(0xFF000000),
    secondary = Color(0xFF232325),
    onSecondary = Color(0xFFFFFFFF),
    background = Color(0xFF121212),
    onBackground = Color(0xFFFFFFFF),
    onBackgroundHigh = Color(0xFFFFFFFF),
    onBackgroundMedium = Color(0xB7FFFFFF),
    onBackgroundLow = Color(0x99FFFFFF),
    error = Color(0xFFFF8C67),
    onError = Color(0xFF000000),
    success = Color(0xFF4AC99B),
    onSuccess = Color(0xFF000000),
    warning = Color(0xFFFFC700),
    onWarning = Color(0xFF000000),
    outline = Color(0xFF2C2C2E),
)

val LocalTColorScheme = staticCompositionLocalOf { lightTColorScheme() }

fun TColorScheme.contentColorFor(color: Color) = when (color) {
    primary -> onPrimary
    secondary -> onSecondary
    background -> onBackground
    error -> onError
    success -> onSuccess
    warning -> onWarning
    else -> color
}
