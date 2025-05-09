package com.team6.smartbudget.core.presentation.designsystem.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.team6.smartbudget.core.ui.R

data class TTypography(
    val heading1: TextStyle,
    val heading2: TextStyle,
    val heading3: TextStyle,
    val heading4: TextStyle,
    val heading5: TextStyle,
    val heading6: TextStyle,
    val bodyXL: TextStyle,
    val bodyL: TextStyle,
    val bodyM: TextStyle,
    val bodyS: TextStyle,
    val bodyXS: TextStyle,
)

fun TextStyle.bold(): TextStyle = copy(fontWeight = FontWeight.ExtraBold)

@OptIn(ExperimentalTextApi::class)
private val defaultFontFamily =
    FontFamily(
        Font(R.raw.manrope_regular, weight = FontWeight.Normal),
        Font(R.raw.manrope_extrabold, weight = FontWeight.ExtraBold),
        Font(R.raw.manrope_bold, weight = FontWeight.Bold),
        Font(R.raw.manrope_medium, weight = FontWeight.Medium),
        Font(R.raw.manrope_light, weight = FontWeight.Light),
        Font(R.raw.manrope_semibold, weight = FontWeight.SemiBold),
        Font(R.raw.manrope_extralight, weight = FontWeight.ExtraLight),
    )

// Needed to suppress because
// it's not necessary to have a single line for each TextStyle
@Suppress("LongMethod")
fun defaultTTypography() = TTypography(
    heading1 = TextStyle(
        fontSize = 56.sp,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = defaultFontFamily,
    ),
    heading2 = TextStyle(
        fontSize = 44.sp,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = defaultFontFamily,
    ),
    heading3 = TextStyle(
        fontSize = 36.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = defaultFontFamily,
    ),
    heading4 = TextStyle(
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = defaultFontFamily,
    ),
    heading5 = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = defaultFontFamily,
    ),
    heading6 = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = defaultFontFamily,
    ),
    bodyXL = TextStyle(
        fontSize = 19.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = defaultFontFamily,
    ),
    bodyL = TextStyle(
        fontSize = 17.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = defaultFontFamily,
    ),
    bodyM = TextStyle(
        fontSize = 15.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = defaultFontFamily,
    ),
    bodyS = TextStyle(
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = defaultFontFamily,
    ),
    bodyXS = TextStyle(
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = defaultFontFamily,
    ),
)

val LocalTTypography = staticCompositionLocalOf { defaultTTypography() }
